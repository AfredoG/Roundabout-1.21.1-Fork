# Проверка порта: незавершённая разработка

База: Roundabout 3.6.3, upstream 6be5c7745095b9864dfaa8b6ec30773810fc448a.
Цель: Minecraft 1.21.1, NeoForge 21.1.265, Java 21, Gradle 8.14.3.

## Результаты

- Gradle help: успешно.
- Подготовка исходников и классов Minecraft/NeoForge: успешно.
- processResources: успешно; проверены метаданные, access transformer, mixin-конфигурации и 141 рецепт.
- Последняя сохранённая полная диагностика compileJava: НЕУСПЕШНО, 1203 ошибки, 18 предупреждений.
- После начала переноса арбалетов наблюдалась проверка с 1184 ошибками; её журнал утрачен при перезапуске среды.
- Текущие исходники после последующих правок НЕ прошли новую компиляцию: загрузка Gradle в восстановленной среде блокируется сетью; также отсутствует полный JDK.
- JAR для установки: отсутствует.
- Запуск клиента и выделенного сервера полного мода: не выполнен (компиляция не проходит).
- Помощник обновления: проверены чистое слияние, конфликт, отказ при незакоммиченных изменениях.
- GitHub Actions: конфигурация добавлена, удалённый запуск не выполнялся.

Список ошибок — build-errors.txt. Ошибки могут быть каскадными; их число не является оценкой процента готовности.
Текущая проверка выполнена на полноценном JDK 21; обход определения пути Java не потребовался.

## Основные категории оставшихся ошибок компиляции

| Ошибка | Количество |
|---|---:|
| cannot find symbol | 676 |
| method does not override or implement a method from a supertype | 140 |
| incompatible types: CompoundTag cannot be converted to Provider | 29 |
| incompatible types: double cannot be converted to LivingEntity | 17 |
| incompatible types: ResourceKey<Enchantment> cannot be converted to Holder<Enchantment> | 11 |
| minecraft has private access in Gui | 11 |
| method isCorrectToolForDrops in class Item cannot be applied to given types; | 9 |
| constructor LootAdder in class LootAdder cannot be applied to given types; | 7 |
| <M,U>register(MenuType<? extends M>,ScreenConstructor<M,U>) has private access in MenuScreens | 7 |
| incompatible types: ItemStack cannot be converted to ItemCost | 6 |
| isCollisionShapeFullBlock(BlockState,BlockGetter,BlockPos) has protected access in BlockBehaviour | 6 |
| package minecraft does not exist | 6 |
| incompatible types: Reference<SoundEvent> cannot be converted to SoundEvent | 5 |
| method getFoodProperties in interface IItemExtension cannot be applied to given types; | 5 |
| incompatible types: BlockGetter cannot be converted to LevelReader | 5 |

## Что требуется закончить

1. Основная сеть переведена на CustomPacketPayload / RegistryFriendlyByteBuf; регистрация и обработчики добавлены в NeoForge. Проверить доставку всех типов сообщений в игре, включая прежние thread_hop сообщения. Отдельная проверка сериализации в предыдущей рабочей среде проходила, но это не проверка полной сети мода.
2. Данные ItemStack: DataComponents, копирование/изменение CustomData, сериализация через HolderLookup.Provider. Простая замена getOrCreateTag на copyTag теряет запись изменений и недопустима.
3. Mixin: проверить цели, дескрипторы, локальные переменные и порядок инъекций по исходникам 1.21.1. Не отключать обязательность инъекций для скрытия ошибок.
4. Рендеринг: оставшиеся VertexConsumer/BufferBuilder, модели, GUI, слои брони и скины.
5. Сущности: проверить Builder-синхронизацию и mixin при запуске; сохранение NBT, атрибуты, перемещение между измерениями, эффекты.
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

## Последний сохранённый этап

- POTION_CONTENTS и PotionData; облака зелий и регистрация рецептов через RegisterBrewingRecipesEvent.
- Материал Snake Skin Boots зарегистрирован отдельно; тег vanishing сохранён для исходных предметов.
- Builder для синхронизируемых данных сущностей и инъекций mixin. Gravity-поля определяются до builder.build() в конструкторе Entity. TrackedData проверяет наличие записи через accessor массива itemsById.
- Новые сигнатуры setupRotations и renderBackground; Pose у вершин рыболовной лески и стрел.
- Stray Cat и шахматные фигуры: изменения CustomData явно записываются обратно.
- BlockSource раздатчиков и износ гарпуна без преждевременного удаления предмета.
- CUSTOM_NAME, noTerrainParticles, ResourceLocation для моделей, аргумент LivingEntity у времени использования предмета.
- DeltaTracker: остановка экранной интерполяции перенесена в client-only TimeStopDeltaTracker; старые инъекции getFrameTime/getDeltaFrameTime и запись во внутренний таймер Minecraft удалены. Накопитель тиков не изменяется. Проверка поведения в игре ещё требуется.
- Последовательные проверки compileJava: 1523 → 1333 → 1220 → 1203 ошибки. Исходники из компиляции не исключались.

## С чего продолжить

1. Открыть port/build-errors.txt. Оставшиеся большие группы: ItemStack/NBT, музыка (RecordItem удалён), зачарования, геометрия сущностей, рендеринг и NeoForge client events.
2. IronBallCrossbowItem переведён на CHARGED_PROJECTILES и draw; проверить заряд, расход слитков, multishot, quick charge, создание IronBallEntity и cooldown после выстрела. Проверить mixin-цели в игре.
3. Перенести музыкальные предметы на JUKEBOX_PLAYABLE/JukeboxSong, включая StereoBlockEntity, MusicDiscController и Whitesnake.
4. Проверить mixin-цели по исходникам 1.21.1. Успешная компиляция helper-классов не проверяет инъекции в игре.
5. Собирать из neoforge: JAVA_HOME должен указывать на полноценный JDK 21, команда ./gradlew compileJava --console=plain. Корневой Gradle относится к исходным загрузчикам.
6. После очередного законченного блока сохранить накопительный патч относительно f8f891f03. При восстановлении из этого архива патч НЕ нужен: все текущие изменения уже включены.

## Контрольная точка после восстановления 11 ноября 2026

- Восстановлены полный архив (2adda81) и более свежий накопительный патч с арбалетами; исходная база и история Git сохранены.
- IronBallCrossbowItem использует CHARGED_PROJECTILES, штатный draw для расхода боеприпасов/числа снарядов и создание IronBallEntity с копией оружия. Отдельно сохранены длительность заряда и 80-тиковый cooldown.
- GravityCrossbowItemMixin перенесён с удалённых getArrow/onCrossbowShot на текущие методы. Исправлена сигнатура отдачи Jumpin Jack Flash и проверка заряженных стрел в рендерере.
- IronBallEntity: новые конструкторы, default pickup item, сериализация ItemStack с registryAccess; invoker приватного setPierceLevel; doPostAttackEffectsWithItemSource.
- Восстановлена незаписанная перед перезапуском правка knockback: удалено shadow отсутствующего поля, используется doKnockback(target, damageSource).
- KnifeEntity, StandArrowEntity, HarpoonEntity и BladedBowlerHatEntity: конструкторы AbstractArrow получают предмет; добавлены безопасные константные getDefaultPickupItem без чтения ещё не инициализированных полей; сериализация предмета получает registryAccess.
- Сборка 11 ноября остановилась при загрузке Gradle 8.14.3: Network is unreachable, в том числе с настройкой прокси. Скачать JDK через Adoptium не удалось (HTTP 403). Новое число ошибок НЕ определено; build-errors.txt относится к более раннему состоянию (1203 ошибки).
- git diff --check проходит. Игровые тесты не запускались. Клиентский/серверный JAR отсутствует.

Следующий шаг: восстановить JDK 21 и доступ к зависимостям, выполнить compileJava из neoforge. Затем закончить остальные подклассы AbstractArrow (пули, Tusk, Ratt, рапира), enchantment API у гарпуна/шляпы и mixin конструкторов с изменённой гравитацией. Отдельно проверить корректность сетевой синхронизации pickupItemStack и дополнительных полей предметов после загрузки мира.
