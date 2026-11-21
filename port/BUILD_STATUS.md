# Проверка порта: незавершённая разработка

База: Roundabout 3.6.3, upstream 6be5c7745095b9864dfaa8b6ec30773810fc448a.
Цель: Minecraft 1.21.1, NeoForge 21.1.265, Java 21, Gradle 8.14.3.

## Результаты

- Gradle help: успешно.
- Подготовка исходников и классов Minecraft/NeoForge: успешно.
- processResources: успешно; проверены метаданные, access transformer, mixin-конфигурации и 141 рецепт.
- compileJava: НЕУСПЕШНО, 152 ошибок, 18 предупреждений (актуальный полный журнал сохранён в build-errors.txt).
- JDK 21 и Gradle восстановлены; подготовка Minecraft/NeoForge и processResources прошли в новой среде.
- JAR для установки: отсутствует.
- Запуск клиента и выделенного сервера полного мода: не выполнен (компиляция не проходит).
- Помощник обновления: проверены чистое слияние, конфликт, отказ при незакоммиченных изменениях.
- GitHub Actions: конфигурация добавлена, удалённый запуск не выполнялся.

Список ошибок — build-errors.txt. Ошибки могут быть каскадными; их число не является оценкой процента готовности.
Текущая проверка выполнена на Azul JDK 21.0.6 и Gradle 8.14.3; зависимости восстановлены заново.

## Категории актуального полного журнала (152 ошибок)

| Ошибка | Количество |
|---|---:|
| cannot find symbol | 67 |
| method does not override or implement a method from a supertype | 23 |
| incompatible types: Reference<SoundEvent> cannot be converted to SoundEvent | 5 |
| non-static method getEquipmentSlotForItem(ItemStack) cannot be referenced from a static context | 2 |
| incompatible types: RecipeHolder<CraftingRecipe> cannot be converted to CraftingRecipe | 2 |
| incompatible types: int cannot be converted to ServerEntity | 2 |
| incompatible types: CompoundTag cannot be converted to Provider | 2 |
| no suitable constructor found for Explosion(ClientLevel,<null>,double,double,double,float,List<BlockPos>) | 2 |
| incompatible types: Holder<SoundEvent> cannot be converted to SoundEvent | 2 |
| method getExperienceReward in class LivingEntity cannot be applied to given types; | 2 |
| no suitable method found for playSound(<null>,ThrownTrident,Holder<SoundEvent>,SoundSource,float,float) | 1 |
| no suitable method found for fromJson(String) | 1 |
| method isPathfindable in class BlockStateBase cannot be applied to given types; | 1 |
| getTeam() in FallenMob cannot override getTeam() in Entity | 1 |
| method writeInventoryToTag in interface InventoryCarrier cannot be applied to given types; | 1 |


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

## Последний этап — проверен компилятором 11 ноября 2026

Этот раздел заменяет прежние ограничения по отсутствию JDK/сети: доступ к загрузкам восстановлен, сборка снова доходит до Java-компиляции.

- ItemStack в 13 классах сущностей сохраняется через saveOptional(registryAccess) и читается через parseOptional. Пустые предметы сохраняются без вызова save, который не допускает пустой стек.
- Manhattan Transfer: раздельные NBT-ключи ManhattanHeldItem и ManhattanHeldItemFull; чтение старого HeldItem оставлено как fallback. Ключ базового StandEntity больше не перезаписывает оба слота.
- JJFLauncherState и его player mixin передают HolderLookup.Provider; сохранены счётчик заряда, копирование предмета и сброс времени выстрела при загрузке.
- CommandDiscController, LivingEntityDiscData, маски игрока: сериализация предметов с реестрами.
- BlackSabbathPlayerInventory: новые сигнатуры SimpleContainer, сохранение Slot вместе с компонентами предмета; пустые позиции не уплотняются.
- Конструкторы семи видов неподбираемых снарядов переведены на API 1.21.1. Внутренний default item непустой (требование AbstractArrow.save), но getPickupItem остаётся EMPTY: подбираемая стрела из них не создаётся.
- GravityAbstractArrowMixin: обновлён дескриптор конструктора; старый локальный ModifyVariable(require=0) заменён Redirect applyGravity. Точка вызова подтверждена javap по compiledWithNeoForge; учитываются getGravity и направление GravityAPI. Игровое применение mixin ещё не проверено.
- Гарпун и шляпа: современная лояльность, эффекты попадания, проверка Channeling, обработка прочности и бонусов урона. Исходная логика возврата в tick сохранена.
- Приватный setPierceLevel вызывается через ранее добавленный invoker. Удалены несуществующие setShotFromCrossbow(false) у классов, которые не имеют оружия-арбалета.
- Проверки compileJava: 1108 → 1078 → 1068 → 1064 ошибки. У этих перенесённых классов снарядов, JJFLauncherState и BlackSabbathPlayerInventory в последнем логе нет ошибок Java; это не проверка поведения в игре.
- git diff --check проходит. Клиент/сервер не запускались; JAR для игры отсутствует.

Продолжать с port/build-errors.txt: музыка и RecordItem, рендеринг/GUI, предметы и эффекты зачарований, события NeoForge. У Manhattan Transfer остаётся перенос захвата/выпуска стрел (getKnockback/setKnockback и createArrow с оружием), что отдельно отмечено компилятором. До игры требуется проверка всех mixin, сохранения/загрузки, сетевой синхронизации и поведения предметов.


## Последний этап — 12 ноября 2026: музыка и еда

Этот раздел уточняет предыдущие планы: основная миграция RecordItem выполнена.

- Музыкальные диски используют JUKEBOX_PLAYABLE и три записи динамического реестра jukebox_song. Сохранены исходные идентификаторы предметов, звуки, переводы, comparator_output и длительности (2840/4380/3720 секунд, как в старых конструкторах RecordItem).
- MusicDiscData централизует получение композиции из компонента стека. Whitesnake, бросаемые предметы, бомбы и инвентарь распознают диски по компоненту. MusicDiscController отслеживает композицию, включая смену компонента у того же Item.
- StereoBlock: установка диска перенесена из mixin удалённого RecordItem в useItemOn; расход предмета, статистика и извлечение сохранены. Старый mixin удалён из конфигурации.
- StereoBlockEntity: интерфейс одиночного контейнера, загрузка предмета с реестрами, событие воспроизведения с ID композиции, длительность и компаратор через JukeboxSong. Сохранены старые NBT-ключи и сигнал; исправлена очистка контейнера. Невалидный диск останавливает воспроизведение.
- Еда: alwaysEdible, ленивые поставщики эффектов, Holder для MobEffectInstance, питание из ItemStack.getFoodProperties(entity). Мясо для волков перенесено в minecraft:wolf_food; особая еда терьера сохранена. Питательность Stray Cat читается до уменьшения стека.
- compileJava: 1064 → 1025 → 1006 ошибок, 18 предупреждений. В MusicDiscData, MusicDiscController, StereoBlock, StereoBlockEntity, ForgeItems, ModFoodComponents, MoldSporesEntity ошибок Java нет. Исходники не исключались из компиляции.
- processResources успешно. Все три JSON-композиции и wolf_food совпадают с исходниками в обработанном каталоге ресурсов; ссылки на звуки и ключи переводов проверены. git diff --check проходит.
- Игровое поведение и mixin ещё не проверены. Полный мод не компилируется; готового JAR нет.

Следующие задачи: ForgeEatingMixin (новый путь потребления еды и насыщение), геометрия/атрибуты сущностей, зачарования и захват стрел Manhattan Transfer, GUI/рендеринг и события NeoForge. Затем обязательны запуск клиента/сервера, проверка mixin, сохранения мира и сети. Для стерео отдельно проверить вставку/извлечение, creative, красный камень, загрузку мира и окончание композиции; для Whitesnake — смену диска и дистанцию слушателей.


## Последний этап — 13 ноября 2026: потребление еды и геометрия

- Среда восстановлена из полного архива 43c396c. JDK 21.0.6, Gradle 8.14.3 и зависимости загружены заново. При запуске в рабочей среде устаревший GRADLE_OPTS указывал на недоступный browser-proxy; сборку удалось запустить с текущим HTTP(S)_PROXY. Настройки прокси не внесены в проект.
- ForgeEatingMixin теперь перенаправляет только FoodData.eat(FoodProperties) внутри Player.eat(Level, ItemStack, FoodProperties). Расход предмета, контейнер после еды, статистика, звук и критерии достижений остаются в штатном коде. Владелец FoodData устанавливается до пополнения голода.
- FoodValues переводит абсолютные saturation points компонента обратно в прежний коэффициент для бонуса Pearl Jam. Сохранены ограничения питания (6) и коэффициента насыщения (1.2), коэффициент 1.5 и округление. 57 численных случаев проверены отдельным Java-запуском, включая нулевую питательность и значения на ограничениях.
- FateFoodDataMixin перехватывает общий add(int,float), а не старый eat(int,float): это охватывает оба пути питания 1.21. Переданное насыщение уже абсолютное и не умножается повторно. Стирание времени и отдельное насыщение вампира сохранены.
- Эффекты еды: FateLivingEntity и StandUserEntity используют ModifyArgs у вызова addEatEffect(FoodProperties), получая исходный ItemStack из окружающего eat. Блокировка вампирами и Hex убирает только список эффектов, не расход еды. Pearl Jam срабатывает после штатных эффектов и до уменьшения стека; соблюдает обе блокировки. Эффекты/частицы еды и напитков выполняются на сервере. Подсказки и отметка pearljamfood используют TooltipContext и CUSTOM_DATA.
- Удалённые setMaxUpStep заменены STEP_HEIGHT; наличие атрибута подтверждено в createLivingAttributes. Обновлены три вызова setTame с применением эффектов приручения.
- Размеры California King Bed, Killer Queen и Whitesnake переведены на getDefaultDimensions. Для Whitesnake сохранены особые высоты глаз при плавании, приседании, управлении и плавлении. Ещё семь старых getStandingEyeHeight перенесены в EntityDimensions.withEyeHeight.
- WaterBreathingOverride сохраняет динамическое поведение прежних override метода canBreatheUnderwater, ставшего final. Общий mixin вызывает его до проверки тегов; Whitesnake дышит под водой только вне удалённого управления, как раньше. Путь NeoForge canDrownInFluidType(WATER) вызывает этот же метод, что подтверждено исходниками. Прочие жидкости не затронуты.
- Точки вызова и сигнатуры проверены по подготовленным исходникам Minecraft/NeoForge 1.21.1 и javap. Это не проверка фактического применения mixin при старте игры.
- compileJava: 1006 → 980 → 968 → 961 ошибка, 18 предупреждений. FoodValues, FoodEffects, ForgeEatingMixin, FateFoodDataMixin, три Energized*Mixin и WaterBreathingOverride не имеют ошибок Java. Исходники из сборки не исключались. git diff --check проходит.
- Полный мод ещё не собирается; клиент/сервер не запускались, игрового JAR нет.

Следующие задачи: оставшаяся геометрия/attachments и рендеринг, замена MobType тегами и динамическими проверками, зачарования/стрелы Manhattan Transfer, оставшиеся ItemStack API и события NeoForge. При первом запуске проверить применение ModifyArgs, запрет эффектов Hex/вампира, Pearl Jam, еду с контейнером, управление Whitesnake под водой и масштабирование сущностей. build-errors.txt соответствует последней компиляции.


## Последний этап — 13 ноября 2026: скины, маскировки и профили рук

- После очистки среды восстановлен полный архив 3a62cb7 (961 ошибка). Незавершённые правки скинов восстановлены по журналу работы; накопительный патч сохранён дважды до завершения новой компиляции.
- Доступ к скинам, плащам, элитрам и типу модели переведён на PlayerSkin. Обновлены рендереры рук, частей тела, клонов, маскировок, слои плащей и вызывающие их способности.
- SkinUtil централизует загрузку. SkinManager 1.21 декодирует уже имеющиеся свойства текстур: для UUID без textures добавлено асинхронное fetchProfile через MinecraftSessionService. Никаких join/get, блокирующих отрисовку; до завершения используется стандартный скин. Кэш ограничен 256 профилями и 10 минутами с момента записи; ошибка запроса не блокирует последующие обновления навсегда. Для offline UUID версии 3 лишний запрос не делается. Переданные textures имеют приоритет над кэшем UUID.
- Убраны дублирующие кэши скинов WhitesnakeDisguiseRenderer и CloneRenderer. Текстура и slim/wide берутся из одного результата. Выбор стандартной модели больше не зависит от подстроки wide в имени файла.
- PlayerEntityAbstractClient перехватывает RETURN getSkin вместо удалённого getSkinTextureLocation. Подмена текстуры сохраняет исходные capeTexture, elytraTexture и модель; исходный объект неизменяемый. Для пользовательской текстуры убраны прежний URL и признак secure, относящиеся к исходной текстуре.
- Предотвращена рекурсия: внутри getSkin нельзя выбирать зомби-модель через EntityRenderDispatcher/ClientUtil.getPlayerModel, поскольку диспетчер тоже вызывает getSkin. Используется model из исходного возвращённого PlayerSkin.
- Серверный поиск профиля Whitesnake/Diver Down обновлён под Authlib 6: без Agent.MINECRAFT, ошибка callback принимает имя. Сохранены фоновый запрос и применение в серверном потоке.
- ProfileData сохраняет прежние поля Id/Name/Properties/Value/Signature, включая несколько свойств с одним именем. Руки Killer Queen записываются через ItemData/CUSTOM_DATA. HandBlockEntity получает registry provider при сетевом getUpdateTag и читает старый HandProfile. HandOwner принимается и строкой, и compound; удалён устаревший verifyTagAfterLoad, который менял только форму имени, не используемый для скина HandProfile.
- Учтено требование Authlib 6 к непустым ссылкам UUID/имени: имя-only legacy профиль получает детерминированный offline UUID; UUID-only получает пустую строку имени; пустой тег возвращает null.
- Отдельный Java-запуск с реальными классами Minecraft/NeoForge проверил round-trip UUID/имени/подписанных и неподписанных свойств, legacy имя/UUID, пустой тег, обе модели рук, сохранение плаща/элитр и их отсутствие. Проверка прошла, сеть в тесте не использовалась.
- compileJava: 961 → 847 → 846 ошибок, 18 предупреждений. SkinUtil, ProfileData, PlayerEntityAbstractClient, HandBlockItem, HandBlockEntity и оба DisguiseService не имеют ошибок Java. git diff --check проходит. Никакие исходники не исключались.
- Полный мод не компилируется, игрового JAR нет; загрузка текстур через сеть, применение mixin и рендеринг в игре ещё не проверены.

Далее: VertexConsumer/BufferBuilder и слои рендера; оставшиеся MobType/зачарования, стрелы Manhattan Transfer, события NeoForge. Проверить при первом игровом запуске обычные/зомби-скины, маски, slim/wide, смену профиля маскировки, отсутствие сети, плащи/элитры, сохранение и размещение рук. Актуальная диагностика — port/build-errors.txt (846 ошибок).


## Контрольный патч — скорость приседания (13 ноября 2026)

Заменены 20 вызовов удалённого getSneakingSpeedBonus в управлении стендами. MovementSpeed.sneaking читает полный атрибут SNEAKING_SPEED без повторного добавления 0.3; для мобов без этого player-only атрибута возвращает 0.3. Сигнатура и семантика сверены с LocalPlayer и Attributes 1.21.1. На момент этой промежуточной записи новая компиляция ещё не выполнена; число 846 выше относится к предыдущему этапу. Следующий шаг — compileJava и обновление диагностики.


Проверка этого блока завершена: compileJava — 826 ошибок, 18 предупреждений (было 846). Ошибок Java в MovementSpeed нет; все 20 старых вызовов устранены. git diff --check проходит. Игровой запуск не выполнен; сборка всего мода пока не проходит. Следующий шаг: VertexConsumer/BufferBuilder и рендеринг либо оставшиеся MobType/зачарования по актуальному port/build-errors.txt. Полный архив содержит уже применённые изменения и историю Git; накопительный патч нужен только при восстановлении старой базы f8f891f03.


## Контрольная точка GitHub — клиентские события (13 ноября 2026)

- Семь экранов меню перенесены с закрытого MenuScreens.register на RegisterMenuScreensEvent в клиентской MOD-шине. Регистрация слоёв блоков остаётся в enqueueWork клиентского setup.
- ModifyBakingResult использует ResourceLocation внутри ModelResourceLocation через id(); отбор двух моделей кислоты по namespace/path сохранён.
- Пустой обработчик клиентского тика переведён на ClientTickEvent.Post вместо удалённого TickEvent.ClientTickEvent.
- API событий сверены с исходниками NeoForge 21.1.265. git diff --check проходит.
- Среда восстановлена из GitHub после очистки. Проверка compileJava ещё не завершена: восстанавливаются Gradle и зависимости. Число 826 относится к предыдущей полной проверке, а не к этому изменению. Игрового JAR нет.


## Контрольная точка GitHub — спавн и торговля (13 ноября 2026)

- Четыре регистрации спавна используют SpawnPlacementTypes вместо удалённого SpawnPlacements.Type. Сохранены heightmap, предикаты и операция OR.
- Входная цена пяти торговых предложений переведена на ItemCost; товары, количества, настройки цен и параметры предложений сохранены. В варианте повреждённой стрелы берётся исходный ItemCost предложения вместо его ItemStack-представления.
- git diff --check проходит. Полная compileJava ожидает восстановления зависимостей; текущий список build-errors.txt пока остаётся результатом предыдущих исходников (826 ошибок).


## Проверка двух контрольных точек (13 ноября 2026)

- Полная compileJava на Azul JDK 21.0.6 и Gradle 8.14.3: **805 ошибок, 18 предупреждений**, было 826/18. В ClientForgeEvents, ClientForgeForgeEvents, ForgeEvents, WanderingTrades и WanderingTraderMixin ошибок Java нет.
- Актуальная диагностика обновлена в port/build-errors.txt; пути нормализованы относительно корня репозитория.
- Семь экранов регистрируются через RegisterMenuScreensEvent; новые типы спавна, ItemCost/getItemCostA и ModelResourceLocation.id сверены с исходниками/маппингами Minecraft 1.21.1 и NeoForge 21.1.265.
- Исходники не исключались, обязательность mixin-инъекций не изменялась. Запуск игры и поведение экранов, торговли и спавна ещё не проверены. Игрового JAR нет.
- Следующие блоки: динамическая регистрация туманных блоков в ForgeBlocks (разделить события BLOCK и ITEM, не менять реестр во время обхода), рендеринг, MobType/зачарования, замена устаревшего события сна.
- В чистой среде нужен полноценный JDK 21: предустановленная урезанная Java не поддержала --release 21 при подготовке Minecraft. Для Java-загрузок в этой среде требуется прокси из текущих HTTPS_PROXY/HTTP_PROXY; адрес нельзя сохранять между командами, он меняется.

- processResources: успешно на текущих исходниках; git diff --check проходит. Код сохранён коммитами e5aea000 и 1e6d1197 в ветке port/neoforge-1.21.1.


## Контрольная точка — динамические туманные блоки (13 ноября 2026)

- ForgeBlocks регистрирует блоки через RegisterEvent/Registries.BLOCK, а предметы создаёт и регистрирует только в событии Registries.ITEM. Используются отдельные флаги завершения; отложенные записи содержат исходный блок для FogBlockItem/FogCoatBlockItem.
- Реестр обходится по снимку stream().toList(), чтобы добавление блоков не изменяло текущую итерацию. Сохранены имена ресурсов, чёрные списки, фильтр размера/свойств и порядок «покрытие, затем блок» во вкладке.
- Проверки формы используют EmptyBlockGetter.INSTANCE и BlockPos.ZERO вместо null. Карты блоков сохраняют порядок вставки. Удалены устаревшие вызовы ForgeRegistry и нерабочий закомментированный вариант.
- Сверены RegisterEvent и GameData NeoForge 21.1.265. Полная compileJava: **796 ошибок, 18 предупреждений** (было 805/18); в ForgeBlocks ошибок Java нет. git diff --check проходит.
- Регистрация при запуске, вкладка и datagen ещё не проверены: полный мод не компилируется, игрового JAR нет. Ресурсы не изменялись.
- Следующие блоки: оставшиеся события NeoForge (сон/платформенные хуки), MobType/зачарования, рендеринг. Актуальная диагностика: port/build-errors.txt.


## Контрольная точка — платформенные хуки и добыча (13 ноября 2026)

- LeftClickBlock: TriState.FALSE вместо Event.Result.DENY; удалена проверка несуществующего общего getResult. Отмена запрещает добычу и в creative; разрешения useBlock/useItem сохраняют прежнее исключение для creative.
- WATER_TYPE/LAVA_TYPE читаются через Holder.value().
- ForgePlayer использует готовый бонус MINING_EFFICIENCY вместо уровня Efficiency и повторного вычисления level²+1; сохранена существующая последовательность применения проклятия руки и бонусов.
- Обычная изменённая скорость и два общих пути добычи силами учитывают BLOCK_BREAK_SPEED и SUBMERGED_MINING_SPEED. Последний заменяет удалённый hasAquaAffinity и жёсткое деление на 5; базовое значение атрибута в воде 0.2, Aqua Affinity меняет его на 1.
- API сверены с исходниками Player/PlayerInteractEvent/NeoForgeMod 1.21.1 / 21.1.265 в предыдущем шаге. git diff --check проходит. Новая compileJava пока не завершена: кэш Gradle очистился за паузу. 796 ошибок в build-errors.txt относятся к предыдущей проверке.
- Игровое поведение, совместимость с защитой территорий и модификаторами добычи ещё не проверены; игрового JAR нет.


## Контрольная точка — регистрация модификаторов эффектов (13 ноября 2026)

- ForgeEffects: melting_health, crippled_speed, swapped_health используют ResourceLocation в пространстве roundabout вместо UUID-строк. Операции ADD_VALUE/ADD_MULTIPLIED_TOTAL сохраняют прежнюю арифметику и значения -0.7/-0.3/-2.
- Новая компиляция ещё ожидается: восстановление зависимостей продолжается. Действующий список 796 ошибок относится к предыдущему проверенному коммиту.


## Контрольная точка — тики старения и восстановления (13 ноября 2026)

- OldEffect и ReconstructionEffect используют shouldApplyEffectTickThisTick и boolean applyEffectTick. Возврат true сохраняет эффект до истечения срока; старение по-прежнему применяется каждый тик, лечение — раз в 20 тиков.
- Модификатор скорости старения переведён на roundabout:old_movement_speed и ADD_MULTIPLIED_TOTAL, значение -0.1 сохранено.
- Сигнатуры и значение результата проверены по MobEffect/MobEffectInstance из подготовленных исходников 1.21.1. Полная проверка текущего набора изменений ещё идёт.


## Проверка платформенных хуков, добычи и эффектов (13 ноября 2026)

- Полная compileJava завершена: **775 ошибок, 18 предупреждений**, было 796/18. ForgePlatformHelper, ForgePlayer, ForgeEffects, OldEffect и ReconstructionEffect не имеют ошибок Java. PlayerEntity всё ещё имеет ошибки в других, пока не перенесённых участках.
- Подготовка Minecraft/NeoForge восстановлена и прошла; компиляция выполнялась на Azul JDK 21.0.6 и Gradle 8.14.3. Ресурсы не изменялись. git diff --check проходит.
- Код сохранён отдельными коммитами faec840, 6ff6c7b и fb0e18e; здесь сохранена обновлённая диагностика.
- В этой среде GRADLE_USER_HOME теперь размещён в build-tools/gradle-cache рядом с проектом; используются актуальный прокси из окружения и системный Java trustStore /etc/ssl/certs/java/cacerts. Эти параметры окружения не добавлены в конфигурацию проекта.
- Полный мод по-прежнему не компилируется; игровой запуск и работа модификаторов/защиты территорий не проверены. Следующие блоки: HallucinationEffect (новый жизненный цикл эффектов), сон, рендеринг, MobType/зачарования.


## Контрольная точка — жизненный цикл галлюцинаций (13 ноября 2026)

- HallucinationEffect: ResourceLocation для замедления, ADD_MULTIPLIED_TOTAL, Holder эффекта, shouldApplyEffectTickThisTick и boolean applyEffectTick. Действия эффекта выполняются на сервере.
- В 1.21.1 add/removeAttributeModifiers больше не получают LivingEntity. Добавлен обязательный HallucinationEffectLifecycleMixin к onEffectAdded/onEffectUpdated/onEffectRemoved: синхронизация индикатора и зависящее от типа сущности замедление выполняются при фактическом изменении эффекта, включая forceAddEffect и возврат скрытого уровня. onEffectStarted намеренно не используется: он вызывается и для отклонённого более слабого уровня.
- Сохранена таблица замедления: уровень I — нет; II — игроки и мобы; III — только игроки. Модификатор -6% временный и не дублируется; снимается при удалении/смене уровня. После загрузки сущности восстанавливается первым тиком эффекта. Индикатор очищается при удалении; периодическая синхронизация сохранена.
- Сигнатуры и порядок вызовов проверены по подготовленным LivingEntity/MobEffect/MobEffectInstance 1.21.1. Новые инъекции используют defaultRequire=1; обязательность не ослаблялась.
- Полная compileJava: **766 ошибок, 18 предупреждений**, было 775/18. В HallucinationEffect и HallucinationEffectLifecycleMixin ошибок Java нет.
- Полный игровой запуск недоступен: остаются ошибки других классов. После первой рабочей сборки проверить молоко/истечение эффекта, обновление I→II→III и обратное, игроков и мобов, перезагрузку мира и индикатор Whitesnake. Следующие блоки: сон, рендеринг, MobType/зачарования.

- processResources проходит; в итоговой mixin-конфигурации новый класс присутствует ровно один раз и defaultRequire=1 сохранён. git diff --check проходит. Это проверка упаковки, не применения mixin в игре.


## Контрольная точка — реестры добычи (14 ноября 2026)

- LootAdder принимает ResourceKey<LootTable> из BuiltInLootTables; внешний getSources/isValidLocation(ResourceLocation) сохранён для существующих потребителей. Поиск идёт через актуальный server.reloadableRegistries(), без кэширования таблиц между reload; на клиенте и для LootTable.EMPTY возвращается false. Состав пулов и вероятности сундуков сохранены.
- BrushableBlockMixin: shadow и сохранённый идентификатор таблицы переведены на ResourceKey<LootTable>, поиск — на reloadableRegistries; добавлена проверка отсутствующего ключа/сервера. Исправлены сравнения с шестью BuiltInLootTables археологии, которые теперь также являются ключами.
- LootPoolMixin: поле entries имеет List вместо массива. Для рыбалки список кандидатов и сумма весов изменяются одним обработчиком до vanilla-проверок; маска сохраняет вес 1. Удалён отдельный поздний обработчик веса перед nextInt, чтобы избежать рассогласования списка и суммы при пустом исходном пуле.
- FishingLootMixin использует новый доступ к таблице и проверяет наличие сервера. GlobalLootModProvider принимает lookup future по API NeoForge; отдельно в datagen он не включён (существующий механизм добавляет добычу через mixin, второй путь не активируется).
- Сверены исходники ReloadableServerRegistries, LootTable, BrushableBlockEntity и GlobalLootModifierProvider. Тип entries и захват RandomSource/List/MutableInt до List.size сверены с байткодом LootPool. CAPTURE_FAILHARD и обязательность инъекций сохранены. git diff --check проходит.
- Полная compileJava ещё ожидается: среда снова восстановилась из старого снимка, состояние cb9b771 получено из GitHub, зависимости загружаются заново. 766 ошибок в build-errors.txt пока относятся к предыдущей проверке. После завершения обновить диагностику.
- В игре ещё требуется проверить сундуки, шесть археологических таблиц, рыбалку (обычный и заменённый datapack пул), /reload. Готового JAR нет.


## Проверка блока добычи (14 ноября 2026)

- Полная compileJava: **752 ошибки, 18 предупреждений**, было 766/18. LootAdder, GlobalLootModProvider, BrushableBlockMixin, LootPoolMixin и FishingLootMixin ошибок Java не имеют. Подготовка Minecraft/NeoForge прошла на Azul JDK 21.0.6 и Gradle 8.14.3.
- Дополнительно проверен байткод BrushableBlockEntity: поле lootTable имеет ResourceKey, setChanged вызывается после очистки поля; сохранение ключа в HEAD позволяет использовать его при добавлении добычи.
- git diff --check проходит. Ресурсы не менялись. Игровой запуск и применение mixin ещё не проверены; не считать сверку байткода тестом реального выпадения добычи.
- Исходники блока сохранены в GitHub коммитом fe0f329, актуальная диагностика — port/build-errors.txt. Далее: оставшиеся событийные/клиентские API, рендеринг, MobType и зачарования.
- При восстановлении среды обязательно сначала fetch и сверка локальной ветки с origin/port/neoforge-1.21.1: здесь повторно восстанавливался старый локальный снимок 4eeb5fa, тогда как GitHub уже содержал cb9b771. Единственная локальная правка совпадала с удалённым файлом; она не потеряна. Источник актуального прогресса — удалённая ветка.


## Контрольная точка — HUD NeoForge (14 ноября 2026)

- ForgeForgeGui теперь нацелен на Gui, без наследования от удалённого ForgeGui. Minecraft/random/tickCount доступны через shadow; rightHeight — поле, добавленное NeoForge. Пустой старый hook pre удалён вместе с несуществующими типами overlay API. Имя файла/запись mixin сохранены.
- Кровавый голод перехватывает актуальный renderFood(GuiGraphics, Player, int, int), использует переданного игрока и координаты. Профилирование и увеличение rightHeight выполняет renderFoodLevel, поэтому обработчик не делает их второй раз.
- Запас воздуха стенда выводится в TAIL renderAirLevel. Используется camera player, резервирование 15px в rightHeight учитывает еду, здоровье транспорта и обычные пузырьки воздуха; поправка 6px соответствует разнице высот 15px и стандартного 9px ряда. Есть защита от нулевой ёмкости и ограничение ширины заполнения.
- HudRendering.rdbt$renderTex: актуальный getPositionTexColorShader, Tesselator.begin, addVertex и buildOrThrow. UV, цвет и порядок четырёх вершин сохранены.
- По исходникам и байткоду Gui проверены имена/типы shadow-полей и дескрипторы renderFood/renderAirLevel/getCameraPlayer; специфичные для NeoForge элементы используют remap=false. Обязательность инъекций не ослаблялась.
- Полная compileJava: **717 ошибок, 18 предупреждений**, было 752/18. ForgeForgeGui и HudRendering не имеют ошибок Java; git diff --check проходит. Ресурсы не менялись.
- Игровой запуск ещё не выполнен. После получения рабочей сборки проверить кровавый голод, отсутствие наложения воздуха на здоровье транспорта, масштаб GUI, подводное дыхание и неполный запас вне воды. Также остаётся аудит других common HUD-инъекций/теневых полей: NeoForge разделил renderPlayerHealth на отдельные слои, отсутствие ошибок Java не доказывает их применимость при запуске.
- Следующие блоки: оставшиеся клиентские API/рендеринг, MobType/зачарования и сон. Актуальная диагностика — port/build-errors.txt.


## Контрольная точка — вершины и буферы рендеринга (14 ноября 2026)

- Девять типов частиц (Air/Baby/Cold Crackle, Diver Down Final, Energy Ripple, Golden Spiral, Heartbeat, Punch Impact/Miss) отправляют четыре вершины через addVertex с float-координатами Vector3f. Порядок вершин, UV, цвет, свет и анимация сохранены.
- Перенесены VertexConsumer-помощники StandArrowRenderer, ModFirstPersonLayers и PlayerLikeBeeStingLayer; вызовы собственных методов vertex не переименовывались. Матрицы нормалей уже используют PoseStack.Pose.
- Экранный огонь и skybox создают новый BufferBuilder для каждой порции геометрии через Tesselator.begin и завершают её buildOrThrow. Та же схема применена к четырёхугольнику оверлея TimeStopGameRenderer и подсветке блоков ClientEffectUtil; последняя проверяет непустой список до создания буфера. Используется существующий getPositionTexColorShader.
- Огонь на сущности уже записывает вершины в MultiBufferSource.entityTranslucent. Удалены параллельное создание/отрисовка пустого Tesselator-буфера и ручное переключение shader/texture/blend: состояние и отрисовкой владеет выбранный RenderType. Это предотвращает ошибку пустого MeshData при механической замене end на buildOrThrow.
- API и жизненный цикл сверены с VertexConsumer, Tesselator, BufferUploader, EntityRenderDispatcher и ScreenEffectRenderer из подготовленных исходников Minecraft/NeoForge 1.21.1.
- Полная compileJava: **655 ошибок, 18 предупреждений**, было 717/18. Во всех 17 изменённых Java-файлах нет ошибок компиляции; git diff --check проходит. Ресурсы не менялись.
- Игрового запуска ещё нет. Отдельно остаётся перенос сигнатур mixin: renderSky теперь принимает Matrix4f вместо PoseStack, renderItemInHand — Camera/float/Matrix4f, ParticleEngine.render — новый NeoForge overload с frustum/predicate. Следующий этап — эти точки интеграции; ошибки других классов по-прежнему блокируют JAR.


## Контрольная точка — интеграция отрисовки и восстановление среды (15 ноября 2026)

- TimeStopParticleEngine больше не копирует ParticleEngine.render. Перехват единственного Particle.render в NeoForge-overload меняет только partialTick; сохраняются штатные frustum, predicate прохода, пользовательские типы частиц, обработка пустого MeshData и crash report. Сохранены исключения ItemPickupParticle и частиц, созданных в TS, обновление preTSTick вне радиуса, блокировка tick и скрытие настоящих частиц в Epitaph. NeoForge-overload имеет полный дескриптор/remap=false, vanilla-вызов — remap=true. Обязательность инъекций сохранена.
- TimeStopGameRenderer использует renderItemInHand(Camera, float, Matrix4f), передаёт исходную матрицу при повторном вызове и сбрасывает защиту через finally. Удалён неверный cast LivingEntity-камеры к Player в bobHurt. TAIL-обработчики Achtung/Metallica захватывают только CallbackInfo.
- Skybox/фрагменты земли получают PoseStack из Matrix4f, как в LevelRenderer 1.21.1; Cloud hook больше не захватывает неиспользуемые аргументы. Другие старые renderLevel-инъекции остаются для отдельного аудита.
- До сброса среды javap подтвердил три полных дескриптора, ровно один Particle.render в нужном overload и штатные frustum/predicate/begin/build. Полная compileJava дала **650 ошибок, 18 предупреждений**, было 655/18. Два прежних getPickRange/hasFarPickRange в AchtungGameRenderer ещё требуют переноса; остальные четыре изменённых класса не имели ошибок Java.
- При следующем сообщении среда снова вернулась к 4eeb5fa. Сохранённый этап f764fb7 восстановлен из GitHub, единственная локальная правка ForgePlatformHelper побайтово совпала с удалённой версией. Пять последних файлов восстановлены по точным правкам из переписки. Журнал на 650 ошибок утрачен: build-errors.txt пока содержит последнюю сохранённую проверку на 655. После восстановления зависимостей повторить compileJava и обновить полный журнал.
- Это сверка исходников/байткода, не игровой тест применения mixin. После сборки проверить остановку/возобновление частиц, Epitaph, подбор предметов, частицы во время TS, skybox/облака, руки, прозрачность и камеру наблюдателя. JAR ещё отсутствует.


## Контрольная точка — узоры знамён и цвет моделей (15 ноября 2026)

- BannerFlagModel получает узоры предмета из DataComponents.BANNER_PATTERNS (пустой компонент — BannerPatternLayers.EMPTY); ручное чтение BlockEntityTag/старых Pattern-хешей удалено. Базовый цвет берётся из BannerItem или BannerBlockEntity и передаётся отдельно.
- TimeStopBannerRenderer и AchtungBannerRendererMixin используют BannerPatternLayers и актуальные перегрузки renderPatterns с DyeColor. Основная ткань рисуется отдельно, затем до 16 узоров в исходном порядке; это соответствует прежним 17 записям с включённым base. Материалы получаются по Holder<BannerPattern>, поэтому работают и ключи из datapack.
- Прозрачные слои Achtung сохраняют entityTranslucent; BannerFlagModel сохраняет entityNoOutline. Цвет передаётся packed ARGB через DyeColor.getTextureDiffuseColor. Обычное знамя без узоров не теряет основной цвет.
- PsuedoHierarchicalModel реализует контракт Model.renderToBuffer с packed ARGB. Старый float-overload оставлен как переходный адаптер для upstream-вызовов и делегирует новой виртуальной перегрузке.
- AchtungModelPart перехватывает render с packed ARGB, а shadow compile соответствует новому API. Сохранены нулевая видимость, смена буфера, исходное правило alpha=ether и голубой цвет заморозки; дочерние части получают тот же цвет.
- Исходники BannerRenderer/BannerPatternLayers/DyeColor/Sheets/ModelPart сверены с 1.21.1. javap подтвердил обе перегрузки renderPatterns и точные render/compile ModelPart. git diff --check проходит. Полная compileJava ожидает восстановление Maven/Gradle-кэша; build-errors.txt пока относится к сохранённым 655 ошибкам до двух последних блоков.
- В игре проверить базовый цвет без узоров, несколько цветных слоёв, предел 16, datapack-узор, узоры щита при прозрачности, знамёна в TS, BannerFlagModel и прозрачность/заморозку моделей с дочерними частями. Отдельно остаются прежние взаимодействия двух banner mixin при одновременных TS/Achtung. Игровой запуск ещё не выполнен.


## Контрольная точка — прицеливание и модель снаряда (15 ноября 2026)

- AchtungGameRenderer больше не повторяет старый pick с getPickRange/hasFarPickRange и фиксированными расстояниями. В актуальном pick(Entity, double, double, float) добавляется условие существования цели к исходному predicate ProjectileUtil. Штатный GameRenderer сохраняет дальность блоков/сущностей из атрибутов игрока, препятствия, фильтрацию расстояния и обновление hitResult/crosshairPickEntity. Без игрока исходный predicate возвращается как есть.
- javap подтвердил точный дескриптор pick, единственный целевой вызов ProjectileUtil.getEntityHitResult и оба последующих filterHitResult. Index=4 соответствует аргументу Predicate. Обязательность инъекций не изменялась.
- HallucinatoryAcidProjectileModel реализует Model.renderToBuffer с packed ARGB для внутренней и внешней геометрии. Специальные renderInner/renderOuter и выбор render type в рендерере оставлены прежними.
- git diff --check проходит. Полная компиляция ещё восстанавливает Minecraft/NeoForge после сброса среды; итоговый журнал будет сохранён отдельно. После успешной сборки проверить прицеливание сквозь скрытую сущность к доступной цели, препятствия, разную дальность блоков и сущностей, creative/survival и модификаторы атрибутов.


## Полная проверка после восстановления среды (15 ноября 2026)

- Восстановлены Gradle 8.14.3 (SHA-256 дистрибутива проверен) и Maven/NeoForm-кэш. Подготовка Minecraft/NeoForge выполнена заново на Azul JDK 21.0.6. Полная compileJava дала **630 ошибок, 18 предупреждений**, было 655/18 в последнем сохранённом журнале (промежуточная проверка до сброса среды — 650/18).
- Последние блоки частиц, неба/рук, знамён, прозрачности моделей, прицеливания и HallucinatoryAcidProjectileModel не имеют ошибок Java. Актуальный полный журнал снова сохранён в build-errors.txt. git diff --check проходит; ресурсы не менялись.
- При повторном восстановлении окружения proxy properties нужно передать и в аргументах Gradle: локальные системные настройки Gradle могут перекрывать JAVA_TOOL_OPTIONS старым адресом proxy. Это только особенность рабочей среды, build scripts проекта не менялись.
- Исходники уже сохранены в коммитах e6cea05, cbd8c8e и a6f112e; эта запись фиксирует их совместную проверку. Игра и применение mixin не запускались; готового JAR нет. Следующие блоки: слои брони, GUI и оставшиеся клиентские API, аудит renderLevel, сущности/предметы/зачарования.


## Контрольная точка — броня клонов и NPC (15 ноября 2026)

- HumanoidLikeArmorLayer перебирает ArmorMaterial.layers(), использует Holder материала и DataComponents.TRIM; выбор inner/outer texture и decal отделки соответствует 1.21.1. Ручная сборка путей по удалённому getName и DyeableArmorItem/DyeableLeatherItem удалена. Выбранная сторонним модом Model используется также для отделки и блеска.
- Через IPlatformHelperClient передаются получение текстуры каждого слоя, базового цвета, цвета слоя и setupModelAnimations. NeoForge-реализация вызывает ClientHooks/IClientItemExtensions, включая пропуск слоя с цветом 0 и единственное получение fallbackColor на предмет. Общие defaults учитывают DYEABLE/DyedItemColor и dyeable-флаг слоя; common-код не привязан к NeoForge-классам.
- FabricPlatformHelperClient больше не реализует устаревший String-getArmorTexture, использует default общего интерфейса. Это согласование общего API, а не проверка или завершение Fabric-порта; собирается только цель NeoForge.
- Сохранены копирование позы, видимость частей, маски, настройки показа брони и снимки ItemStack в остановленном времени. Актуальные параметры анимации передаются расширению предмета.
- API сверены с HumanoidArmorLayer/ArmorMaterial и IClientItemExtensions/ClientHooks NeoForge 21.1.265. Полная compileJava: **618 ошибок, 18 предупреждений**, было 630/18. В HumanoidLikeArmorLayer, IPlatformHelperClient и ForgePlatformHelperClient ошибок Java нет. git diff --check проходит; ресурсы не менялись.
- В игре ещё проверить все слоты, окрашенную кожаную броню и overlay, отделку/decal, зачарованный блеск, многослойный материал и модовую модель/текстуру/анимацию, смену экипировки в TS. Готового JAR нет.


## Контрольная точка — инвентарь тумана и меню TS (15 ноября 2026)

- FogInventoryScreen использует SessionSearchTrees текущего соединения. NeoForge-адаптер выбирает собственные ключи индексов имён/тегов для выбранной вкладки через CreativeModeTabSearchRegistry; при перестроении содержимого обновляет индексы всех вкладок с поиском. Общий default остаётся на vanilla-индексах, без NeoForge-импортов в common.
- Перестроение выполняется при CreativeModeTabs.tryRebuildTabContents, без повторного поиска на каждом кадре/тике. Пустая вкладка тумана остаётся пустой, без перехода к стандартной вкладке обычных предметов. Сохранены поиск по имени/#тегу, фильтр FogBlockItem, положение прокрутки при обновлении и сброс при новом запросе.
- Перенесены EditBox.moveCursorToEnd(false), четыре аргумента mouseScrolled, Item.TooltipContext и DataComponents.CREATIVE_SLOT_LOCK. Удалён прежний EditBox.tick. Скроллеры/табы используют GUI-спрайты вместо удалённого tabs.png.
- PauseTSScreen использует OptionsScreen из нового пакета, Minecraft.disconnect/GenericMessageScreen и сохранённый ServerData для выбора возврата из Realms. Значок черновика жалобы — icon/draft_report. isPauseScreen=false и tickAllTimeStops сохранены.
- Screen.render теперь сам вызывает renderBackground: убраны дополнительные вызовы из обоих экранов. Для компактного режима PauseTSScreen фон отключён через override renderBackground; полный режим рисует его один раз.
- По исходникам сверены CreativeModeInventoryScreen, PauseScreen, Screen и SessionSearchTrees. В Minecraft resources JAR проверено наличие 31 используемого/выбираемого GUI-спрайта. Полная compileJava: **605 ошибок, 18 предупреждений**, было 618/18. Четыре изменённых Java-класса не имеют ошибок компиляции; git diff --check проходит. Новые ресурсы не добавлялись, processResources повторно не запускался.
- После рабочей сборки проверить поиск по именам/тегам после входа, смены языка и обновления вкладок, колесо/перетаскивание/resize, блокированные слоты и подсказки; меню TS, опции, выход из одиночной игры/сервера/Realms и черновик жалобы. Сейчас клиента/сервера и готового JAR всё ещё нет.
- Продолжение: оставшиеся клиентские API (ZPlayerRender/scoreboard/скины), обязательные renderLevel/HUD mixin, сущности/предметы, MobType/зачарования и сон. Источник состояния — origin/port/neoforge-1.21.1; перед любыми правками после восстановления среды сделать fetch и сравнить локальные изменения с удалёнными.


## Контрольная точка — окраска бабочек (15 ноября 2026)

- Небольшой блок: только ButterflyRenderer и ButterflyColorLayer. TextureAndColorProvider передаёт packed ARGB из DyeColor.getTextureDiffuseColor для тела, внутренней и внешней части крыльев. Слой передаёт этот цвет в новую сигнатуру coloredCutoutModelCopyLayerRender; промежуточные float-массивы больше не нужны.
- Сверены DyeColor (цвет имеет непрозрачный alpha) и RenderLayer 1.21.1. Выбор текстур, три независимых цвета и порядок параметров анимации сохранены. Все использования TextureAndColorProvider найдены в этих двух файлах.
- Полная compileJava до сброса среды: **601 ошибка, 18 предупреждений**, было 605/18. В двух изменённых классах ошибок Java нет. git diff --check проходит. Игровая проверка пока невозможна, JAR отсутствует.
- Следующее сообщение снова восстановило старый локальный снимок 4eeb5fa; GitHub-этап 5885f2d получен заново. Два файла восстановлены точно: их Git blob SHA совпали с проверенными до сброса (752947659… и d56da38e0…). Новых изменений логики после проверки нет.
- Полный журнал на 601 ошибку был утрачен при сбросе до удалённого сохранения. build-errors.txt и таблица категорий остаются подлинным предыдущим журналом на 605 ошибок; они не переписывались вручную. При следующем рабочем запуске compileJava сохранить новый полный журнал. В BUILD_STATUS/GITHUB_IMPORT отдельно отмечен результат выполненной проверки на 601.
- После получения рабочей сборки проверить окраску всех трёх частей бабочки для четырёх вариантов узора. Следующие задачи остаются прежними; перед продолжением сначала fetch удалённой port/neoforge-1.21.1.


## Контрольная точка — подписи игроков (15 ноября 2026)

- ZPlayerRender использует актуальный renderNameTag с partialTick. Значение проходит через специальные подписи масок/маскировки и шесть веток отображения имени превращённого игрока; правила скрытия и creative override сохранены.
- Счёт под именем берётся через DisplaySlot.BELOW_NAME и getPlayerScoreInfo, без создания/изменения записи на рендере. ReadOnlyScoreInfo.safeFormatValue и numberFormatOrDefault(StyledFormat.NO_STYLE) сохраняют формат числа и корректно обрабатывают отсутствие счёта, как PlayerRenderer 1.21.1.
- Масштаб ModificationMaskItem читает один снимок modifications через ItemData.elementOrEmpty; удалён старый getTag guard. Пустой компонент не меняет масштаб; формулы height/width сохранены.
- Сверены PlayerRenderer/EntityRenderer/ItemData. javap подтвердил полный дескриптор цели с IF)V; git diff --check проходит. Полная compileJava запущена после восстановления Gradle/зависимостей, результат будет сохранён отдельно. Последний полный журнал пока относится к 605 ошибкам, последняя выполненная проверка до этого блока — 601/18.
- После рабочей сборки проверить обычные/заменённые имена, все настройки скрытия, objective belowName (включая пользовательский формат и отсутствующий score), поворот головы и TS, размеры ModificationMaskItem. Игра ещё не запускалась.

- Проверка этого этапа завершена: полная compileJava — **594 ошибки, 18 предупреждений**, было 601/18. В ZPlayerRender ошибок Java нет. Подлинный полный журнал и таблица категорий обновлены; утраченный журнал предыдущего этапа больше не нужен для продолжения. Игровые и Mixin runtime-проверки остаются впереди.


## Контрольная точка — рендереры сущностей и гравитационные подписи (15 ноября 2026)

- GravityEntityRendererMixin больше не копирует старый renderNameTag целиком. Redirect меняет только cameraOrientation: при обычной гравитации возвращает исходный кватернион, при остальных направлениях сохраняет прежнюю формулу на новой копии. Штатные NAME_TAG attachments, partialTick, NeoForge distance hook, фон и направление текста остаются в EntityRenderer 1.21.1.
- javap подтвердил точный дескриптор renderNameTag с IF)V и единственный вызов cameraOrientation. Сигнатура Redirect захватывает dispatcher и аргументы метода; сверена с документацией SpongePowered Mixin. Применение mixin в работающем клиенте ещё не проверено.
- CloneRenderer читает modifications через ItemData одним снимком, сохраняя формулы роста/ширины и исключение для FogCloneEntity. WhitesnakeDisguiseRenderer передаёт partialTick в подпись.
- TerrierEntityRenderer использует непрозрачный packed ARGB для мокрой шерсти и сбрасывает tint в -1, как WolfRenderer. PlayerLikeArrowLayer создаёт декоративную Arrow с ItemStack.EMPTY/null, как vanilla ArrowLayer. RCoreShader использует POSITION_TEX_COLOR; ShaderInstance 1.21.1 привязывает атрибуты по их именам, GLSL-входы Position/Color/UV0 сохранены.
- Полная compileJava — **587 ошибок, 18 предупреждений**, было 594/18. Во всех шести изменённых классах ошибок Java нет; полный журнал и таблица обновлены, git diff --check проходит. Ресурсы не менялись.
- После рабочей сборки проверить подписи при всех шести направлениях гравитации и near/far, маски/масштаб клонов, имя Whitesnake, мокрых/сухих терьеров, декоративные стрелы и reload core shaders. Готового JAR нет.
- Продолжение: интерфейсы инвентарей/внешности и конфигурации, затем renderLevel/HUD mixin и оставшиеся сущности/предметы. Перед возобновлением выполнить fetch ветки порта.


## Контрольная точка — экраны инвентарей, внешности и настроек (15 ноября 2026)

- ClientUtil делегирует подсказки штатному Screen.getTooltipFromItem, сохраняя TooltipContext текущего мира и NeoForge ClientTooltipFlag. FogInventoryMenu различает HUMANOID_ARMOR и ANIMAL_ARMOR при быстром переносе, как InventoryMenu 1.21.1.
- В HairColorChangeScreen, NailColorChangeScreen, ModificationVisageScreen и двух helper-методах PowerInventoryScreen используется PoseStack.scale с отрицательным Z, обновляющий также нормали. Ориентация камеры предпросмотра соответствует InventoryScreen 1.21.1: conjugate на копии и rotateY(PI), без изменения входного quaternion.
- BlackSabbathPlayerInventoryScreen использует уже существующий переносимый helper PowerInventoryScreen.renderEntityInInventoryFollowsMouse2: прежние пиксельные координаты, масштабы 20/24 и точка слежения за мышью сохранены. Удалена повторная отрисовка фона в этом экране и PowerInventoryScreen; в трёх экранах внешности super.render вызывается перед панелями один раз.
- MemoryReadingScreen читает сохранённый инвентарь через ItemStack.parseOptional с registryAccess мира на init, когда Minecraft уже привязан к экрану; при resize инвентарь предварительно очищается с сохранением 41 слота NonNullList. Нет обращения к неинициализированному minecraft из конструктора. Панель рисуется в renderBackground, затем кнопки, содержимое и подсказки; фон не перекрывает кнопки преобразования дисков.
- FateDeathScreen использует disconnect/GenericMessageScreen и GUI-спрайт icon/draft_report. Градиент вынесен в renderBackground, текст и hover-effect больше не перекрываются повторным фоном. Задержка кнопок и прежнее поведение возрождения сохранены.
- DiverDownCraftingScreen передаёт актуальные WidgetSprites в ImageButton, сохраняя собственную отрисовку двух областей исходной текстуры и переключение книги рецептов. ConfigListWidget задаёт высоту viewport height-60, верх 20 и setX; ConfigScreen не рисует фон и зарегистрированный список повторно.
- Удаление modifications в копии предмета проходит через ItemData.update, без старого removeTagKey. Новых ресурсов нет; наличие draft_report спрайта проверено в Minecraft resources JAR. API/порядок отрисовки сверены с Screen, AbstractContainerScreen, InventoryScreen, DeathScreen, AbstractSelectionList и NonNullList 1.21.1.
- Полная compileJava — **571 ошибка, 18 предупреждений**, было 587/18 (в начале текущего продолжения 601/18). Во всех двенадцати изменённых Java-классах ошибок нет. Полный журнал и таблица обновлены, git diff --check проходит. Клиент/сервер ещё не запускаются, готового JAR нет.
- После рабочей сборки проверить масштаб/освещение и имена в предпросмотрах, все варианты Black Sabbath, sliders/save/cancel/resize, предметы с компонентами на memory disc и подсказки, кнопку преобразования, книгу рецептов и конфигурацию при разных GUI scale, экран смерти и выход. Отдельно предстоит аудит прочих screen render и renderLevel/HUD mixin.


## Контрольная точка — точки подключения рендеринга мира (15 ноября 2026)

- ZLevelRenderer, ZWorldRenderer и ShaderLevelRenderer используют точный renderLevel(DeltaTracker, boolean, Camera, GameRenderer, LightTexture, Matrix4f, Matrix4f). Значение partialTick берётся через getGameTimeDeltaPartialTick(false), как у LevelRenderer 1.21.1. Сохранены обработка памяти Anubis, Epitaph/привязок, подсветки D4C/Diver Down, обводки при управлении стендом, интерполяция дня и callback завершения рендера.
- PoseStack для обводки, Epitaph/привязок и подсветки создаётся с единичной матрицей: в этих точках LevelRenderer уже применил modelViewMatrix через RenderSystem. Повторно умножать на матрицу камеры нельзя — это повернёт эффекты дважды. Порядок до первого/второго endLastBatch и до DebugRenderer.render сохранён.
- ShaderLevelRenderer перенесён на Lighting.setupLevel()/setupNetherLevel() без Matrix4f, сохраняя момент подготовки depth buffer/списка TS bubbles после terrain и до сущностей. ShaderGameRenderer принимает DeltaTracker в render и использует getGameTimeDeltaPartialTick(true), как GameRenderer.renderLevel; счётчик кадров остаётся на HEAD renderLevel, без захвата несуществующих аргументов.
- Исправлены Shadow isChunkCompiled -> isSectionCompiled в двух LevelRenderer mixin; убран Final с изменяемого ClientLevel в ZWorldRenderer. Неиспользуемый Shadow tick удалён из ShaderGameRenderer: такого поля в GameRenderer 1.21.1 нет. Пустые upstream callback-заготовки сохранены с CallbackInfo; неиспользуемый захват locals удалён.
- javap по собранному neoforge-21.1.265.jar подтвердил все три дескриптора render/renderLevel, Shadow isSectionCompiled и INVOKE-цели: по одному setupLevel()/setupNetherLevel(), один DebugRenderer.render и doEntityOutline, три endLastBatch (используются ordinal 0/1). Исходники подтверждают применение RenderSystem modelView перед сущностями/обводками. Это проверка байткода, не запуск Mixin в клиенте.
- Повторная полная compileJava: **571 ошибка, 18 предупреждений**, как до этого блока; в четырёх изменённых классах ошибок Java нет. Диагностика сохранена, git diff --check проходит. Новые ресурсы и зависимости не добавлялись, обязательные mixin не отключались. Полный порт ещё не собирается.
- Следующий приоритет: GravityGameRendererMixin, ForgeGameRendererMixin и WhitesnakeControlGravityCameraMixin всё ещё используют старый renderLevel(FJ, PoseStack) и ordinal mulPose 3/4. В 1.21.1 view rotation строится через camera.rotation().conjugate и Matrix4f.rotation до prepareCullFrustum; переносить совместно, учитывая двойную коррекцию Whitesnake и исключение пилотирования Manhattan Transfer. Затем HudRendering (удалённые screenWidth/screenHeight и разделение слоёв NeoForge), остальные screen render и gameplay API (MobType, зачарования, сущности, сон).
- Проверка в игре после рабочей сборки: camera/culling при всех направлениях гравитации, TS/Made in Heaven, Nether/обычное освещение, Fast/Fancy/Fabulous, Epitaph, обводки/выделение D4C и Diver Down, callbacks и post shaders. Сейчас запуск и готовый JAR отсутствуют.


## Контрольная точка — гравитация и камера стендов (15 ноября 2026)

- В 1.21.1 GameRenderer строит view matrix из camera.rotation().conjugate, а GravityCameraMixin уже формирует rotation = inverse(gravity) * cameraAngles. Повторные world-rotation hooks из 1.20.1 давали бы двойной поворот. GravityGameRendererMixin оставляет только обновление/применение статуса гравитации на HEAD текущего renderLevel(DeltaTracker).
- GravityCameraMixin больше не копирует и не отменяет Camera.setup. ModifyArgs меняет только смещение глаз в его setPosition(DDD); quaternion корректируется непосредственно после rotationYXZ, до вычисления forwards/up/left. Selector setRotation* находит математическую часть и vanilla FF, и NeoForge FFF overload; в NeoForge она существует только в FFF, значит поворот применяется один раз. Сохраняются camera roll, частичный тик камеры, события NeoForge, native zoom и collision.
- PilotingCamera вызывает штатный setup для живой управляемой сущности с защитой от повторного входа, затем восстанавливает связь публичного Camera.entity с телом игрока в finally. Сохранены controlledLerpAngleDegrees, отдельная интерполяция высоты глаз, isDetached, очистка недействительного POV, PreTSTick и исключение пассажира из заморозки интерполяции. Скопированные варианты setup и устаревшие Shadow move(DDD)/getMaxZoom(D) удалены.
- Whitesnake/Silver Chariot меняют yaw/pitch в обоих setRotation overload через wildcard; преобразование в зеркальном третьем лице остаётся прежним и идемпотентно при делегировании FF -> FFF. Гравитация выбирается по действующему POV, включая Whitesnake/Manhattan Transfer, а не по телу игрока: отдельная поправка focus^-1 * controlled больше не нужна.
- ForgeGameRendererMixin и WhitesnakeControlGravityCameraMixin удалены вместе с их регистрациями: их ручной world rotation заменён штатным view matrix от уже скорректированной Camera. Это устранение дублирования перенесённой логики; оставшиеся camera mixin и defaultRequire=1 сохранены.
- Проверка исходников и javap: setup имеет один setPosition(DDD) и по одному getViewXRot/getViewYRot, partialTickTime записывается до setRotation(FFF); единственный rotationYXZ находится в FFF и предшествует обновлению трёх осей камеры. События ComputeCameraAngles и getDetachedCameraDistance сохранены штатным setup. Обе mixin JSON корректны; git diff --check проходит.
- Проверка завершена: processResources успешно, повторная полная compileJava — **571 ошибка, 18 предупреждений**, без новых ошибок после блока камеры. Во всех шести изменённых Java-классах ошибок нет. Актуальный полный журнал сохранён. В обработанных ресурсах проверено отсутствие регистраций двух удалённых дублирующих mixin, required=true и defaultRequire=1 сохранены. Клиент ещё не запускался; это не проверка применения Mixin во время запуска.
- В игре после рабочей сборки проверить шесть направлений и анимацию смены гравитации, фрустум/руку/частицы, first/third-person и mirrored, разный масштаб и стены, roll от другого мода, body/POV с разной гравитацией, Whitesnake/Silver Chariot/Manhattan Transfer, выход/смерть стенда, остановку времени и пассажира.


## Контрольная точка — общий HUD и слои способностей (15 ноября 2026)

- HudRendering, AirPressureHudMixin и WhitesnakeHotbarMixin принимают GuiGraphics/DeltaTracker в актуальных точках подключения. Все ссылки на удалённые Gui.screenWidth/screenHeight заменены текущими размерами GUI. Прицел, эффекты и оверлеи сохранены; временная подмена crosshairPickEntity для Warhammer теперь восстанавливает также исходный null.
- Скопированная отрисовка здоровья/брони/голода/воздуха из 1.20.1 удалена из общего mixin. Кровавый голод и дополнительный кислород уже перенесены в ForgeForgeGui на штатные слои NeoForge. Серые сердца при остановке времени применяются к renderHearts с защитой повторного входа и finally, без повторного изменения leftHeight и состояния мигания; Locacaca/Stand Virus накладываются один раз вместе с сердцами.
- Число опыта в 1.21.1 имеет отдельный renderExperienceLevel. Замещающие полосы способностей и DiscSeal через IHudAccess отмечают занятую область уровня в текущем кадре; отметка сбрасывается на HEAD Gui.render. Значок прыжка заменяет число только при обычной доступной полосе опыта, не перекрывая другую способность. Тепло и Infinite Spin рисуются один раз на пути с отменённой или обычной XP/jump bar.
- Whitesnake заменяет renderItemHotbar, сохраняя внешний renderHotbar для общих оверлеев/давления и штатный выбор spectator. Удалённый widgets.png заменён hud/hotbar и hud/hotbar_selection через blitSprite; размеры рамки выбора соответствуют 1.21.1. Индикатор Jumpin’ Jack Flash и DiscSeal используют GuiGraphics.guiWidth/guiHeight.
- javap подтвердил цели и Shadow всех четырёх HUD mixin, включая дескрипторы GuiGraphics/DeltaTracker, renderHearts и отдельный renderExperienceLevel. У методов с TAIL один return; у renderHealthLevel одно резервирование leftHeight до renderHearts. Сравнение с чистым Minecraft подтвердило, что внешний renderHotbar добавлен NeoForge: для него указано remap=false, vanilla renderItemHotbar и остальные методы остаются с обычным remap. Оба новых hotbar-спрайта присутствуют в Minecraft resources.
- После успешной сборки проверить TS/Locacaca/Stand Virus, броню/поглощение/верховую езду, кровавый голод и кислород, все замены полос и обычный XP, прыжок, DiscSeal, heat/Infinite Spin, управляемый Whitesnake и плавление, карту Lyin Eyes, Warhammer, давление JJF, spectator и изменение GUI scale. Работа клиента ещё не проверена.

- Повторная полная compileJava расширенного блока HUD: **571 ошибка, 18 предупреждений**; во всех пяти изменённых Java-файлах ошибок нет. processResources прошёл (up-to-date), git diff --check проходит. Это проверка Java и целей инъекций, не запуск Mixin/игры. Следующий этап: оставшиеся screen/gameplay API, включая MobType/зачарования, сущности, сон и перемещение между измерениями.


## Контрольная точка — выброшенные предметы и компоненты (15 ноября 2026)

- FakeItemEntity сравнивает стеки через штатный ItemEntity.areMergable: проверяются предел стака, тип и все компоненты, включая CUSTOM_DATA, имя и зачарования. Старое сравнение произвольного NBT удалено. FakeItemEntity и BombPlantedItemEntity используют FIRE_RESISTANT и ItemStack.canBeHurtBy, что сохраняет проверку компонента и NeoForge item hook.
- Перенос FakeItemEntity между измерениями принимает DimensionTransition и сохраняет последующее объединение соседних имитаций; запрет перехода BombPlantedItemEntity перенесён на canChangeDimensions(Level, Level). Видимость getBlockPosBelowThatAffectsMyMovement соответствует public API 1.21.1.
- MaskItemEntity использует transmuteCopy для смывания крови: сохранены количество и component patch. FancyLighterItemEntity копирует полный ItemStack, меняет сохранённый компонент состояния и публикует через setItem. Оба обработчика обновляют существующий ItemEntity вместо discard/addFreshEntity; поэтому не теряют thrower/cachedThrower, pickup target/delay, возраст, движение, поворот и прочие данные сущности. Устаревшие UUID-вызовы setThrower и лишние Shadow удалены.
- Пересечение котлов проверяется anyMatch: одно превращение на тик, без повторного порождения сущности при нескольких подходящих блоках. Условия воды/дождя/водяного котла и прежнее условие состояния зажигалки сохранены.
- Источник 1.21.1 подтверждает: transmuteCopy переносит count/component patch, copy сохраняет полную карту компонентов, setItem обновляет SynchedEntityData; canBeHurtBy вызывает NeoForge hook и проверяет FIRE_RESISTANT. javap подтвердил tick/getItem/setItem, areMergable и новые дескрипторы переходов между измерениями. Это не проверка сетевой доставки в запущенной игре.
- Полная compileJava — **553 ошибки, 18 предупреждений**, было 571/18. Во всех четырёх изменённых классах ошибок нет. Актуальный журнал и таблица обновлены; git diff --check проходит. Готового JAR пока нет.
- После рабочей сборки проверить маску/зажигалку в воде, дожде и котле с именем и CUSTOM_DATA, количество больше единицы, pickup restrictions и прежний возраст; компоненты и предел объединения имитаций, огонь/лаву, портал имитаций и запрет перехода предмета-бомбы. Далее остаются MobType/зачарования, атрибуты/предметы, движения/измерения сущностей, прочие GUI/mixin и обязательный запуск клиента/сервера.


## Контрольная точка — пакет общих API блоков (16 ноября 2026)

- Работа ведётся группами повторяющихся ошибок API. Сохранён исходный снимок ec5d539; локальная среда была очищена, репозиторий восстановлен из GitHub. Последняя подтверждённая compileJava до текущего пакета: 553/18.
- 23 реализации isPathfindable переведены на BlockState/PathComputationType; условия AIR/LAND/WATER, OPEN/IN_FOG и вызов super у BarbedWireBundle сохранены. Учтены также старые методы без @Override, которые не попадали в журнал ошибок. Для перенесённых override добавлены отсутствовавшие аннотации, чтобы будущий дрейф API обнаруживался компилятором.
- Девять getCloneItemStack принимают LevelReader; три isValidBonemealTarget и вызов Whitesnake больше не используют удалённый флаг стороны. Восемь playerWillDestroy возвращают результат super после исходной логики разрушения частей/контейнера/эффектов. Ничего не заменялось заглушками.
- Семь getUpdateTag передают HolderLookup.Provider далее в сохранение данных. У InvisiBlockEntity восстановление вызывает loadWithComponents с реестрами уровня, а update tag использует ключ OriginalState, который читает loadAdditional. Шесть внешних вызовов collision shape переведены с защищённого метода Block на BlockState.
- Проверка завершена: JDK 21.0.6/Gradle 8.14.3 и Minecraft/NeoForge восстановлены. processResources прошёл; полная compileJava — **477 ошибок, 18 предупреждений**, было 553/18 (снижение на 76). Актуальный журнал и таблица сохранены. javap по Mojang-mapped Minecraft 1.21.1 подтвердил сигнатуры pathfinding, pick-block, bonemeal, разрушения и registry-aware NBT; тела изменённых методов проверены. git diff --check проходит. Полный мод ещё не запускается.
- После успешной сборки проверить двери/проволоку/туман при поиске пути, pick-block растений/шахмат/hand block/Oasis, костную муку и Whitesnake, составные статуи/столы/стул в creative/survival, синхронизацию данных блок-сущностей и восстановление скрытых контейнеров.


## Контрольная точка — общие API сущностей, эффектов и предметов (16 ноября 2026)

- Группа небольших правок в 38 Java-файлах. Пять проверок прямого урона используют isDirect; четыре обхода целей ИИ сохраняют фильтр WrappedGoal.isRunning поверх getAvailableGoals().stream(), чтобы не останавливать/обрабатывать неактивные цели. Шесть bounding box типов строятся через getDimensions().makeBoundingBox; SCUTE заменён TURTLE_SCUTE в трёх местах.
- Пять override lerpTo и оба вызова из TS tick перешли на шесть аргументов. Три запрета перехода между измерениями принимают исходный/целевой Level; StandEntity и RattEntity принимают DimensionTransition, сохраняя отзыв стенда, выбрасывание переносимых предметов и очистку. Три запрета игровых событий снарядов используют Holder<GameEvent>.
- Пять обращений к мгновенным эффектам разыменовывают Holder через value(). Black Sabbath использует igniteForSeconds; Road Roller запрещает igniteForTicks, поскольку igniteForSeconds в 1.21.1 final и делегирует туда. Warding подключается к igniteForTicks(I)V с сохранением условий огня/лавы и surpassesFire; обе новые формы поджога охвачены.
- Проверки еды с доступным ItemStack/сущностью используют getFoodProperties(entity), включая NeoForge hook, Hex, Whitesnake, уклонение/прыжок и Pearl Jam. Неиспользуемый Shadow Item.isEdible удалён. В Item-only canCombatModeUse сохранена проверка стандартного FOOD и виртуальная диспетчеризация ограничений Ratt/Tusk/Anubis. Проверки типа прочного инструмента используют getMaxDamage()>0, сохраняя защиту также для UNBREAKABLE, как старый Item.canBeDepleted.
- API и семантика проверены по собранным исходникам Minecraft/NeoForge 21.1.265. Полная compileJava: **420 ошибок, 18 предупреждений**, было 477/18; суммарно от начала пакетов 553 -> 420. Актуальный полный журнал и таблица сохранены. git diff --check проходит. Порт ещё не собирается, запуска клиента/сервера нет.
- Проверить после успешной сборки: прямые/косвенные атаки, сброс целей GE/мобов, TS-интерполяцию, границы призыва Black Sabbath/D4C, запрет порталов объектов и отзыв стендов, отсутствие событий пузырей, мгновенные эффекты JJF, Warding/Road Roller/Black Sabbath, Hex на полном голоде, автопилот Whitesnake, съедобные стеки с компонентами и защиту инструментов/снарядов с UNBREAKABLE.


## Контрольная точка — два этапа взаимодействия с блоками (16 ноября 2026)

- 18 блоков подключены к useItemOn и useWithoutItem через общий прежний use-handler. Блоки: растения/Locacaca/горшок, кровавая маска, бензин/зажигалка, покрытие White Album/замороженный/скрытый блок, Flesh, дверь/стул/стол, шахматная доска/игровой стол, десерт, кровать и гроб. Тела всех 18 прежних методов сравнивались с предыдущим коммитом: отличаются только пять вызовов super.use, переведённых в super.useWithoutItem.
- Новый BlockInteractionCompat хранит преобразование результатов в одном месте. PASS после уже выполненного общего обработчика становится SKIP_DEFAULT_BLOCK_INTERACTION: действие не повторяется в useWithoutItem, а использование предмета (например, костной муки) остаётся доступным. Прямой useWithoutItem вызывает тот же handler с MAIN_HAND; useItemOn сохраняет фактическую руку.
- Пять вызовов взаимодействия из Whitesnake, TW/SP, 20th Century Boy и снаряда JJF используют BlockState API через helper. Сохраняются позиция, игрок и рука; позиция BlockHitResult согласуется с исходным явным BlockPos. Для единственного прямого действия способности vanilla default-фаза доступна также при OFF_HAND, как прежний Block.use; сам предмет helper дополнительно не использует. NeoForge UseItemOnBlockEvent проходит через штатный BlockState.useItemOn.
- Полная compileJava: **394 ошибки, 18 предупреждений**, было 420/18. За три пакета 553 -> 394, минус 159 ошибок. Полный журнал и таблица обновлены, git diff --check проходит. Ошибок Java в новом helper и адаптерах фаз нет; прочие ошибки этих классов ещё могут оставаться.
- port/tests/BlockInteractionCompatCheck.java скомпилирован отдельно вместе с helper против реального neoforge-21.1.265.jar и зависимостей Gradle; Java main завершился с **PASS: 42 block interaction cases**. Проверены преобразование шести результатов, отсутствие повторного callback и все 36 сочетаний item-phase/default-result. Это тест переходов между фазами, не запуск клиента/сервера и не игровая проверка содержимого мира.
- После рабочей сборки проверить основную/вторую/пустую руку, костную муку и урожай, воспламенение/размораживание/снятие крови, подсаживание/сон/меню/поводки, нажатие кнопок/рычагов через все перечисленные способности, клиент-серверное соответствие, отмену NeoForge event и отсутствие повторных звуков/расхода/выдачи предметов.
- Следующие выгодные группы: система зачарований/атрибутов инструментов, остатки ItemStack NBT, посадка/смещения сущностей и остальные block/entity overrides. Нельзя отключать обязательные mixin или удалять механику ради снижения счётчика ошибок. Готового JAR ещё нет.

- Финальное сравнение диагностик по файлу/типу ошибки между всеми тремя пакетами подтвердило отсутствие новых категорий ошибок в конкретных файлах: текущие ошибки — оставшаяся часть исходных. Повторная проверка после финальной правки — 394/18.


## Контрольная точка — кодеки блоков, 20 ноября 2026

- Добавлены штатные simpleCodec для 30 конкретных классов блоков, включая отдельные наследники растений, масок и HandBlock. AbstractHandBlock оставлен абстрактной базой: в реестре создаётся только HandBlock, который фиксирует PLAYER и имеет собственный codec.
- Все новые кодеки и прежний кодек StereoBlock зарегистрированы в NeoForge BLOCK_TYPE через отдельный ForgeBlockTypes; реестр подключён к mod bus. Идентификаторы блоков и сериализация состояний мира не менялись.
- Конструктор AjaOreBlock явно передаёт нулевой опыт, как прежний конструктор DropExperienceBlock(Properties).
- Это промежуточное сохранение исходников. Зависимости после очистки среды восстанавливаются; обновлённая полная компиляция ещё не завершилась. Счётчик 394/18 и build-errors.txt пока относятся к проверке 16 ноября. git diff --check проходит.


## Контрольная точка — атрибуты оружия и инструменты, 20 ноября 2026

- Первый пакет кодеков проверен полной compileJava: 394 -> 368 ошибок, 18 предупреждений. Дополнен собственный кодек AjaOreBlock; всего в ForgeBlockTypes зарегистрированы 32 кодека (31 новый и прежний StereoBlock).
- Ножницы, жертвенный кинжал, гарпун и знак получают ATTRIBUTE_MODIFIERS через общий ItemCombatData с прежними значениями урона/скорости и слотом MAINHAND. Glaive/LuckSword, ExecutionerAxe и Warhammer используют штатные createAttributes в новых конструкторах. Неактуальные Multimap/UUID overrides удалены; индивидуальные компоненты стека теперь учитываются штатным API. Bowler Hat сохраняет отсутствие бонусов.
- Сохраняется фактическая прочность TieredItem от материала: прежние вызовы defaultDurability после super уже не влияли на созданный предмет, поэтому удалены. Для помады и двух типов шахматных предметов прочность 6/3/3 задаётся до super.
- ItemCombatData.attackDamage читает только ATTACK_DAMAGE для MAINHAND, без базового урона владельца и зачарований. На него переведены шесть обращений Black Sabbath и ThrownObjectEntity к удалённым getter оружия.
- Ножницы, кинжал и молот принимают ItemStack в isCorrectToolForDrops. Восемь проверок уровня добычи стенда используют стандартные стеки кирок; молот проверяет фактический выбранный стек. Сохранены ограничение молота рудами и запрет добычи каменной маски. GRASS заменён SHORT_GRASS.
- Lyin Eyes использует стабильный ResourceLocation модификатора, ADD_VALUE и hasModifier(id). У BCSoldier getAttributeValue принимает Holder<Attribute>, поэтому множители здоровья/скорости связки снова переопределяют метод LivingEntity.
- Полная compileJava: **308 ошибок, 18 предупреждений** (было 368/18; суммарно 394 -> 308). Сравнение диагностик по файлам, типам и символам не обнаружило новых ошибок. git diff --check проходит.
- ItemCombatDataCheck прошёл **13 сценариев** против реальных классов NeoForge 21.1.265: стандартные мечи/кирки, пустой стек, отсутствие атрибутов, четыре набора оружия, исключение OFFHAND. Для повторения нужны Java 21, compileClasspath проекта и client-extra resources JAR; harness создаёт пустой LoadingModList и выполняет Minecraft Bootstrap. Полный мод при этом не загружается, тест не является запуском клиента/сервера.
- После рабочей сборки проверить подсказки и урон оружия в обеих руках, атрибуты изменённых стеков, добычу всех уровней стенда/молота, боевое использование вещей Black Sabbath, связку солдат и Lyin Eyes. Дальше — остатки ItemStack/NBT/износа и spawn hooks.


## Контрольная точка — данные предметов, износ и жизненный цикл, 20 ноября 2026

- Чтение старого ItemStack NBT переведено на ItemData для флагов дисков, кислотных снарядов, владельца Stray Cat/зажигалки, шахмат и скинов Anubis. Изменённые копии тегов California и Memory/AnubisSkin явно записываются обратно; простая замена getTag на copyTag не использовалась.
- Для ванильных свойств использованы отдельные компоненты: FIREWORKS, ENTITY_DATA у трёх картин, CUSTOM_MODEL_DATA Anubis, UNBREAKABLE, PROFILE головы игрока и ENCHANTMENT_GLINT_OVERRIDE еды Pearl Jam. Рука читает длительность ракеты до расхода последнего предмета. Pearl Jam больше не вызывает enchant(null, 0), а меняет копию ItemStack и передаёт её ItemEntity для синхронизации.
- SpawnEggItem получает полный ItemStack. Сериализация имени Stray Cat получает registryAccess; прочность Flesh Bucket читается из фактического стека. После этой группы compileJava: **279/18**, было 308/18.
- Двенадцать вызовов удалённого ItemStack.hurt в руке, Stand Arrow, метаемых предметах, кинжале и нити используют общий ItemDurability.damageWithoutBreaking. Сохраняются проверки creative, флаги результата и самостоятельная обработка разрушения вызывающим кодом; стек helper не уменьшает. Учитываются UNBREAKABLE, эффекты прочности и advancement. Новый overload Level выполняет расчёт только на ServerLevel, без небезопасного cast клиента.
- Пять override finalizeSpawn перешли на четыре параметра с сохранением выбора варианта/стенда/возрастной группы. Обновлены четыре прямых вызова (включая превращения NPC/GE) и оба mixin Mob/ZombieVillager. Удалённый NBT-параметр в телах обработчиков не использовался.
- Обновлены два override и четыре mixin dropCustomDeathLoot: ServerLevel, DamageSource, boolean. Дополнительные дропы Stray Cat, Anubis, Metallica и метеоритов сохранены. ZMob получает Looting через Holder из реестра зачарований и EnchantmentHelper.getEnchantmentLevel; бонусные броски метеоритов не теряются из-за удаления старого аргумента looting.
- Четыре проверки Binding Curse переведены на PREVENT_ARMOR_CHANGE, включая Black Sabbath, маску и превращение Zombie Aesthetician.
- Итоговая полная compileJava: **251 ошибка, 18 предупреждений**. Цепочка за продолжение: **394 -> 368 -> 308 -> 279 -> 251**, минус **143** ошибки. Диагностики сравнивались по файлам, типам и символам: новых групп/увеличений нет. git diff --check проходит. Журнал и таблица актуальны.
- Запуск этой группы не проверял игру и применение mixin. После успешной сборки проверить сохранение/загрузку шахмат/дисков, синхронизацию Pearl Jam, последний фейерверк в руке, головы/картины/яйца призыва, сломанные предметы и зачарования прочности, все варианты спавна/превращения NPC, дополнительные дропы и Looting. Полный мод ещё не собирается; играбельного JAR нет.
- Дальше: MobType и динамическая нежить; эффекты зачарований/стрел/трезубца; посадка и геометрия сущностей; остатки сохранения инвентарей и NeoForge event API. Не считать количество ошибок процентом готовности и не ослаблять mixin ради сборки.


## Контрольная точка: типы мобов и боевые зачарования (2026-11-20)

- Полная компиляция: 251 → 183 → 181 ошибок, 18 предупреждений. Удалено 70 диагностик; новых категорий ошибок по сравнению с исходным журналом нет.
- Удалённый MobType заменён тегами entity_type: нежить (включая шесть видов миньонов, Zombiefish, три вида Fallen и ZombieAesthetician) и FallenSpider. JSON остаются в исходных каталогах upstream; processResources переносит пути в формат 1.21.1.
- Для зомби-превращения отдельной сущности сохранены инверсия лечения/урона, иммунитет к яду/регенерации и чувствительность к Smite. EntityPredicate учитывает зомби-состояние только для тегов undead/sensitive_to_smite; обычные и прямые проверки типов продолжают работать штатно.
- Урон глефы, ножей, метаемого оружия, Black Sabbath и агрессивных мобов использует EnchantmentHelper.modifyDamage с настоящими DamageSource и ItemStack. Эффекты после попадания перенесены на doPostAttackEffectsWithItemSource; серверная проверка вынесена в EnchantmentCompat. Сохранены особые коэффициенты защиты мода.
- Отбрасывание способностей теперь расширяет modifyKnockback: сохраняет базовую силу и не складывается повторно с более сильным зачарованием. Усиление не применяется к попаданиям обычных снарядов.
- Верность/Тягун трезубцев переведены на эффекты зачарований. Молотам добавлен тег enchantable/sharp_weapon взамен mixin удалённого DamageEnchantment.
- Удалён Shadow уже отсутствующего Mob.maybeDisableShield и его мост: в 1.21.1 отключение щита при блокировании уже выполняет Player.blockUsingShield.
- processResources: успешно. Содержимое всех трёх новых тегов проверено в собранных ресурсах. По исходникам 1.21.1 проверены сигнатуры четырёх изменённых целей mixin и единственный перехватываемый вызов EntityTypePredicate.matches.
- Это проверка компиляции, ресурсов и целей API. Запуск полного мода и поведение зачарований в игре ещё не проверены; готового JAR нет.


## Контрольная точка: стрелы и оружие снарядов (2026-11-21)

- Полная compileJava: 181 → 152 ошибки, 18 предупреждений. Исправлен также промежуточный недостающий импорт Items; новых диагностик относительно предыдущей точки нет.
- RoundaboutArrowItem принимает исходный лук/арбалет и передаёт его StandArrowEntity. Снаряд использует modifyDamage и штатный doKnockback, сохраняя собственные правила достойности и получения стенда.
- Полная копия BowItem.releaseUsing удалена вместе с прежним mixin: штатный ProjectileWeaponItem вызывает исправленную фабрику стрел, расходует боеприпасы и прочность, применяет зачарования и флаг intangible для творческого режима. Существующий подбор stand/worthy-стрел через PlayerEntity сохранён. Vanilla Infinity проверяет конкретно minecraft:arrow, поэтому stand-стрелы по-прежнему расходуются.
- Фабрики стрел мобов получают настоящий лук, ручные броски получают null вместо оружия. Обновлены конструкторы обычных и спектральных стрел; бомбы Killer Queen получают копию исходного ItemStack с данными зелий.
- Manhattan Transfer сохраняет копию оружия перехваченной стрелы и записывает её в NBT. При выстреле копия восстанавливается без повторного запуска projectile-spawn эффектов. При замене/очистке заряда ссылка очищается. Доступ к приватным firedFromWeapon/setPierceLevel оформлен через accessor/invoker.
- Зачарования оружия AnubisGuardian получаются из registry; имена Power/Punch обновлены.
- Проверены пути создания/подбора стрел и сигнатуры accessor по исходникам 1.21.1. Полный игровой запуск ещё невозможен из-за оставшихся ошибок; стрельба и перехват требуют проверки в игре после завершения компиляции.
