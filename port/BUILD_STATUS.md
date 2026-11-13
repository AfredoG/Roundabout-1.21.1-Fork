# Проверка порта: незавершённая разработка

База: Roundabout 3.6.3, upstream 6be5c7745095b9864dfaa8b6ec30773810fc448a.
Цель: Minecraft 1.21.1, NeoForge 21.1.265, Java 21, Gradle 8.14.3.

## Результаты

- Gradle help: успешно.
- Подготовка исходников и классов Minecraft/NeoForge: успешно.
- processResources: успешно; проверены метаданные, access transformer, mixin-конфигурации и 141 рецепт.
- compileJava: НЕУСПЕШНО, 1819 ошибок, 18 предупреждений.
- JAR для установки: отсутствует.
- Запуск клиента и выделенного сервера полного мода: не выполнен (компиляция не проходит).
- Помощник обновления: проверены чистое слияние, конфликт, отказ при незакоммиченных изменениях.
- GitHub Actions: конфигурация добавлена, удалённый запуск не выполнялся.

Список ошибок — build-errors.txt. Ошибки могут быть каскадными; их число не является оценкой процента готовности.
Текущая проверка выполнена на полноценном JDK 21; обход определения пути Java не потребовался.

## Основные категории оставшихся ошибок компиляции

| Ошибка | Количество |
|---|---:|
| cannot find symbol | 1107 |
| method does not override or implement a method from a supertype | 179 |
| incompatible types: UUID cannot be converted to Entity | 30 |
| incompatible types: CompoundTag cannot be converted to Provider | 29 |
| incompatible types: double cannot be converted to LivingEntity | 17 |
| incompatible types: Pose cannot be converted to Matrix3f | 16 |
| method setupRotations in class LivingEntityRenderer<T,M> cannot be applied to given types; | 16 |
| method getUseDuration in class ItemStack cannot be applied to given types; | 12 |
| method renderBackground in class Screen cannot be applied to given types; | 12 |
| incompatible types: ResourceKey<Enchantment> cannot be converted to Holder<Enchantment> | 11 |
| minecraft has private access in Gui | 11 |
| incompatible types: Holder<Potion> cannot be converted to Potion | 10 |
| method isCorrectToolForDrops in class Item cannot be applied to given types; | 9 |
| constructor LootAdder in class LootAdder cannot be applied to given types; | 7 |
| <M,U>register(MenuType<? extends M>,ScreenConstructor<M,U>) has private access in MenuScreens | 7 |

## Что требуется закончить

1. Основная сеть переведена на CustomPacketPayload / RegistryFriendlyByteBuf; регистрация и обработчики добавлены в NeoForge. Проверить доставку всех типов сообщений в игре, включая прежние thread_hop сообщения. Отдельная проверка сериализации в предыдущей рабочей среде проходила, но это не проверка полной сети мода.
2. Данные ItemStack: DataComponents, копирование/изменение CustomData, сериализация через HolderLookup.Provider. Простая замена getOrCreateTag на copyTag теряет запись изменений и недопустима.
3. Mixin: проверить цели, дескрипторы, локальные переменные и порядок инъекций по исходникам 1.21.1. Не отключать обязательность инъекций для скрытия ошибок.
4. Рендеринг: оставшиеся VertexConsumer/BufferBuilder, модели, GUI, слои брони и скины.
5. Сущности: завершить Builder-синхронизацию, включая mixin; сохранение NBT, атрибуты, перемещение между измерениями, эффекты.
6. Предметы и ресурсы: новая система брони, зачарований, еды, зелий, музыкальных дисков; loot/advancement/worldgen JSON и новые теги.
7. NeoForge-события и клиентская регистрация.
8. После успешной компиляции — запуск клиента и выделенного сервера, затем проверка всех стендов, инвентарей, сохранений, измерений и сетевой синхронизации.

Успешная компиляция сама по себе не доказывает правильность mixin и игровых механик.

## Восстановленный этап

- Основной транспорт MessageC2S/MessageS2C: payload-кодеки, регистрация, разделение клиента и сервера. Старый перехват payload заменён штатными обработчиками; инъекция действий игрока сохранена.
- CustomData: явное сохранение изменений патронов, перезарядки, памяти дисков, опыта, сумок тел и модификаций внешности. Прочие старые обращения к ItemStack ещё требуют переноса.
- Штатные компоненты BLOCK_STATE, CUSTOM_MODEL_DATA и FIREWORKS для соответствующих предметов.
- VertexConsumer, часть матриц нормалей, ModelResourceLocation, подсказки и сериализация BlockEntity с реестрами.
- Износ экипировки и сохранение исчерпанной стрелы без удаления предмета.
- Цветные частицы Purple Haze: MapCodec и StreamCodec.

Часть предыдущих незапакованных правок была утрачена при перезапуске среды и восстановлена заново. Числа и диагностика выше относятся к последнему повторному запуску compileJava. Проверка git diff --check проходит. Список ошибок не является оценкой процента готовности.

## Контрольные копии

После каждого законченного блока сохраняется накопительный Roundabout_port_checkpoint.patch относительно коммита f8f891f03 исходного архива. Патч содержит и новые файлы; локальный коммит сам по себе не заменяет сохранение копии вне рабочей среды. Полный архив содержит уже применённые изменения — повторно накладывать патч на него не нужно.
