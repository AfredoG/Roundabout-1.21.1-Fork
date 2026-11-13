# Проверка порта: незавершённая разработка

База: Roundabout 3.6.3, upstream 6be5c7745095b9864dfaa8b6ec30773810fc448a.
Цель: Minecraft 1.21.1, NeoForge 21.1.265, Java 21, Gradle 8.14.3.

## Результаты

- Gradle help: успешно.
- Подготовка исходников и классов Minecraft/NeoForge: успешно.
- processResources: успешно; проверены метаданные, access transformer, mixin-конфигурации и 141 рецепт.
- compileJava: НЕУСПЕШНО, 2359 ошибок, 18 предупреждений.
- JAR для установки: отсутствует.
- Запуск клиента, выделенного сервера и игровые проверки: не выполнены.
- Помощник обновления: проверены чистое слияние, конфликт, отказ при незакоммиченных изменениях.
- GitHub Actions: конфигурация добавлена, удалённый запуск не выполнялся.

Список ошибок — build-errors.txt. Ошибки могут быть каскадными; их число не является оценкой процента готовности.
В среде разработки использован временный внешний обход определения пути Java в NeoForm (ProcessHandle.command возвращал пустое значение). Он не включён в мод и не нужен на обычной системе, где этот API работает.

## Основные категории оставшихся ошибок компиляции

| Ошибка | Количество |
|---|---:|
| cannot find symbol | 1385 |
| method does not override or implement a method from a supertype | 243 |
| incompatible types: Holder<MobEffect> cannot be converted to MobEffect | 85 |
| constructor ModelResourceLocation in record ModelResourceLocation cannot be applied to given types; | 47 |
| incompatible types: EquipmentSlot is not a functional interface | 43 |
| incompatible types: CompoundTag cannot be converted to Provider | 33 |
| incompatible types: UUID cannot be converted to Entity | 30 |
| incompatible types: MobEffect cannot be converted to Holder<MobEffect> | 18 |
| incompatible types: double cannot be converted to LivingEntity | 17 |
| method setupRotations in class LivingEntityRenderer<T,M> cannot be applied to given types; | 16 |
| method saveAdditional in class BlockEntity cannot be applied to given types; | 14 |
| method getUseDuration in class ItemStack cannot be applied to given types; | 12 |
| method renderBackground in class Screen cannot be applied to given types; | 12 |
| incompatible types: ResourceKey<Enchantment> cannot be converted to Holder<Enchantment> | 11 |
| minecraft has private access in Gui | 11 |

## Что требуется закончить

1. Основная сеть net.zetalasis.networking: перевести пользовательские пакеты, регистрацию и обработчики на CustomPacketPayload. Два дополнительных Forge-канала уже переписаны, но вся сеть ещё не перенесена.
2. Данные ItemStack: DataComponents, копирование/изменение CustomData, сериализация через HolderLookup.Provider. Простая замена getOrCreateTag на copyTag теряет запись изменений и недопустима.
3. Mixin: проверить цели, дескрипторы, локальные переменные и порядок инъекций по исходникам 1.21.1. Не отключать обязательность инъекций для скрытия ошибок.
4. Рендеринг: оставшиеся VertexConsumer/BufferBuilder, модели, GUI, слои брони и скины.
5. Сущности: завершить Builder-синхронизацию, включая mixin; сохранение NBT, атрибуты, перемещение между измерениями, эффекты.
6. Предметы и ресурсы: новая система брони, зачарований, еды, зелий, музыкальных дисков; loot/advancement/worldgen JSON и новые теги.
7. NeoForge-события и клиентская регистрация.
8. После успешной компиляции — запуск клиента и выделенного сервера, затем проверка всех стендов, инвентарей, сохранений, измерений и сетевой синхронизации.

Успешная компиляция сама по себе не доказывает правильность mixin и игровых механик.
