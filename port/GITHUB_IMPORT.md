# GitHub checkpoint and recovery

Imported on 2026-11-13 into AfredoG/Roundabout-1.21.1-Fork, branch `port/neoforge-1.21.1`.

## Status

This is an INCOMPLETE development port: Minecraft 1.21.1, NeoForge 21.1.265, Java 21, Gradle 8.14.3.
Last full compilation after subsequent port fixes: 796 Java errors and 18 warnings. No playable JAR; client and dedicated server have not been validated.
See BUILD_STATUS.md and build-errors.txt for diagnostics and remaining work.
This import changes no mod source code and does not constitute a new build test.

## Import verification

Upstream baseline: `6be5c7745095b9864dfaa8b6ec30773810fc448a`.
Ten original development commits were replayed through the GitHub API.
Every imported Git tree SHA was checked against its original local tree SHA and matched exactly.
Commit IDs differ because commit metadata changed. Each imported message records Original-port-commit.
The original archive retains the original Git history. Do not merge both copies of the development history.

| Original commit | GitHub commit |
| --- | --- |
| aa4b527bc56a74881c2c1f2fa9330755624c5a6b | e96ef3d05af285d517ffd94d1147ae8a3869f196 |
| f8f891f03831fe93e90b82c8d09ec7e3fbab8460 | 689656aa6a5c8685abc8e9442b1a8c1bbb89a009 |
| bd7007c7caab0dfbce21f74f84fa759a2e9938f5 | ddd9187056d81b411c456dc76da0b732ba570ebf |
| 2adda8195bf3bf40570d5b5bd1975d61265f926e | d391c14b025ad4bb244a4df681c9e0203d872c7c |
| c89942899f23a6bac23ad87157ab97051e610903 | 6c63ea1fe59353f9b54da01ce667a90c4465098c |
| 0d581364da10b5c7ac48f654e6a05aca7c1cc60c | f8333ef0d9197ff4c36cb4f184faf88d12c37443 |
| 43c396c5c3582ada6031705b4bfef19a6a324f4e | 9d810b9758d483fb54f265097cda8d99905f7906 |
| 3a62cb7f246c0a3a877d70264af32a4c38a4fce2 | bb2f7968c46c42e081b6d5ca8820d19439eb18b2 |
| a7e53e999b3b738e9f2a240dc9cbb788ebebe6f5 | 8103815d8718cb0d158b7c8be0e233ea067e9a17 |
| 82e26c35bfe07dc810a869495e20f425f9b192bf | 3ae00759ae90614ece5aa48f4b77175589bce329 |

This document is a separate checkpoint after the identical source import.

## Resume after workspace reset

Clone the fork and select the port branch:

```sh
git clone --branch port/neoforge-1.21.1 https://github.com/AfredoG/Roundabout-1.21.1-Fork.git RoundaboutMod
cd RoundaboutMod
git remote add upstream https://github.com/Hydraheads/RoundaboutMod.git
```

Read port/BUILD_STATUS.md, port/build-errors.txt, and the existing port documentation before editing.
Follow existing port/update_upstream.py workflow for upstream updates; retain upstream history.
Use Java 21 and the standalone neoforge Gradle project. Restore dependencies when needed.
Next work: remaining rendering APIs (VertexConsumer/BufferBuilder), MobType/enchantments and NeoForge events.
Do not exclude failing classes or disable required mixins to manufacture a successful build.

Save small coherent checkpoints frequently to this remote port branch, including current diagnostics and next steps.
Verify the remote branch head after each save; a local commit alone is not a durable remote backup.
If using the GitHub connector, use expected_sha when updating the branch and verify the created tree.
If using Git, fetch the remote history before continuing. Do not push the old archive history over the imported branch.
Do not force-push master or apply the cumulative legacy patch to this already-patched branch.
