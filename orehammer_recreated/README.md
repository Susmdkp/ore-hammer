# Ore Hammer Recreated (`orehammer_recreated`)

Recreacion independiente (codigo y texturas propios) de las mecanicas de Ore Hammer para **Minecraft Java 26.3 + Fabric**.

## Versiones verificadas
| Componente | Version | Fuente |
|---|---|---|
| Minecraft | 26.3 (15 sep 2026) | fabricmc.net/2026/09/15/263.html |
| Java | 25 | tablas de compatibilidad de mods 26.3 |
| Fabric Loader | 0.19.5 | idem |
| Fabric API | 0.163.0+26.3 (existen posteriores) | listado de versiones |
| Loom / Gradle | 1.17 / 9.6.0 | blog de Fabric 26.3 |
| Mappings | ninguno (juego sin ofuscar) | docs de Loom |

## Instalacion (jugador)
1. Instala Fabric Loader 0.19.5+ para 26.3 y Java 25.
2. Pon Fabric API 0.163.0+26.3 (o superior) y el JAR del mod en `mods/`.
3. En servidor dedicado: mismo JAR en `mods/` del servidor; los clientes tambien lo necesitan (objetos nuevos).

## Compilar (desarrollo)
```
gradle wrapper --gradle-version 9.6.0     # genera gradlew (el JAR del wrapper no se incluye)
./gradlew build                           # JAR en build/libs/orehammer_recreated-1.0.0.jar
./gradlew runClient / runServer
python3 tools/validate.py                 # comprobacion estatica de recursos
```

## Estructura
- `registry/` ModItems, ModRecipes, ModCreativeTabs
- `recipe/CrushingRecipe` tipo de receta propio `orehammer_recreated:crushing` (consume durabilidad del martillo)
- `config/ModConfig` `config/orehammer_recreated.json` (`hammer_damage_per_craft`, por defecto 1)
- `resources/assets` modelos, definiciones de objeto, texturas 16x16, `en_us`, `es_es`, `es_mx`
- `resources/data/orehammer_recreated/recipe` 23 trituraciones + martillo + 3 horno + 3 alto horno + compat opcional

## Recetas
| Entrada | Salida |
|---|---|
| Raw Copper / Copper Ore / Deepslate Copper Ore | 2 / 3 / 4 Raw Copper Dust |
| Raw Iron / Iron Ore / Deepslate Iron Ore | 2 / 3 / 4 Raw Iron Dust |
| Raw Gold / Gold Ore / Deepslate Gold Ore | 2 / 3 / 4 Raw Gold Dust |
| Nether Gold Ore | 1 Raw Gold Dust |
| Coal Ore / Deepslate | 2 / 3 Coal |
| Redstone Ore / Deepslate | 6 / 7 Redstone |
| Nether Quartz Ore | 3 Quartz |
| Emerald Ore / Deepslate | 2 / 2 Emerald |
| Diamond Ore / Deepslate | 2 / 2 Diamond |
| Lapis Ore / Deepslate | 11 / 13 Lapis Lazuli |
| Amethyst Cluster | 8 Amethyst Shard |
| Ancient Debris + Blaze Powder + martillo (fila, con forma) | 2 Netherite Scrap |
| Raw Iron/Copper/Gold Dust (horno 10 s, alto horno 5 s) | Iron/Copper/Gold Ingot (0.4/0.4/0.5 XP) |

Martillo: `Copper Block, Iron Block, Copper Block / _, Stick, _ / _, Iron Nugget, _`.
Todas las trituraciones son sin forma (mineral + martillo) salvo Ancient Debris.

Compat opcional (Create, Create Ironworks, Create More Additions, AE2) en `data/orehammer_recreated/recipe/compat/`.

## Estado de verificacion
Ver `PENDIENTES.md` y la seccion de pruebas en la respuesta de entrega.
