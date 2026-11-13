#!/usr/bin/env python3
"""Inspect upstream updates; optionally merge on a new, uncommitted review branch.

Requires Python 3.10+ and Git. This never pushes or publishes anything.
"""
import argparse
import json
import subprocess
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
UPSTREAM = ROOT / "port/upstream.json"


def git(*args, check=True):
    result = subprocess.run(["git", *args], cwd=ROOT, text=True,
                            stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if check and result.returncode:
        raise RuntimeError(result.stderr.strip() or result.stdout.strip())
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--ref", default="master", help="Upstream branch, tag, or commit")
    parser.add_argument("--apply", action="store_true", help="Merge into a NEW review branch")
    parser.add_argument("--offline", action="store_true", help="Use already fetched objects")
    args = parser.parse_args()
    cfg = json.loads(UPSTREAM.read_text(encoding="utf-8"))
    if args.ref.startswith("-"):
        parser.error("A ref cannot begin with '-'")
    if args.apply and git("status", "--porcelain").stdout.strip():
        raise RuntimeError("Commit or stash your changes before --apply.")
    existing = git("remote", "get-url", "upstream", check=False)
    if existing.returncode:
        git("remote", "add", "upstream", cfg["repository"])
    elif existing.stdout.strip() != cfg["repository"]:
        raise RuntimeError("Remote 'upstream' points to another repository; no changes made.")
    if not args.offline:
        git("fetch", "upstream", args.ref)
        target_ref = "FETCH_HEAD"
    else:
        target_ref = args.ref
    target = git("rev-parse", "--verify", "--end-of-options", target_ref + "^{commit}").stdout.strip()
    base = cfg["base_commit"]
    if git("merge-base", "--is-ancestor", base, target, check=False).returncode:
        raise RuntimeError("Target is not a descendant of the recorded upstream base.")
    if git("merge-base", "--is-ancestor", base, "HEAD", check=False).returncode:
        raise RuntimeError("Current checkout does not contain the recorded upstream base.")
    print(f"Upstream base: {base}\nCandidate:     {target}")
    if target == base:
        print("No new upstream commits.")
        return
    print(git("diff", "--stat", base, target, "--", "common", "forge", "gradle.properties").stdout)
    files = git("diff", "--name-only", base, target).stdout.splitlines()
    for title, marker in [("Mixins", "/mixin/"), ("Networking", "/networking/"),
                          ("Resources", "/resources/"), ("Registration", "/registry/")]:
        matches = [p for p in files if marker in p]
        print(f"{title}: {len(matches)} changed files")
    if not args.apply:
        print("Review only. Use --apply to create a branch and attempt a merge.")
        return
    branch = "update/neoforge-1.21.1-" + target[:12]
    git("switch", "-c", branch)
    # Reuse reviewed conflict resolutions, but leave them unstaged for inspection.
    git("config", "rerere.enabled", "true")
    git("config", "rerere.autoupdate", "false")
    result = git("merge", "--no-ff", "--no-commit", target, check=False)
    print(result.stdout + result.stderr)
    if result.returncode:
        raise RuntimeError("Merge needs attention on " + branch +
                           ". Inspect git status; resolve conflicts or run git merge --abort.")
    cfg["base_commit"] = target
    cfg["status"] = "merged-needs-build-and-runtime-validation"
    UPSTREAM.write_text(json.dumps(cfg, indent=2) + "\n", encoding="utf-8")
    print("Merge is NOT committed. Build from neoforge/, test client AND dedicated server,")
    print("then review git diff HEAD, git add the reviewed changes, and git commit.")
    print("If abandoning the merge: git merge --abort; git restore port/upstream.json")


if __name__ == "__main__":
    try:
        main()
    except (RuntimeError, OSError) as exc:
        raise SystemExit(str(exc))
