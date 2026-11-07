# Estado de verificacion

## Resuelto con Ore Hammer 2.1.0 (1.20.1)
Los .json del jar original confirman la tabla completa de trituracion, el martillo, y los tiempos/XP de horno y alto horno
(200/100 ticks; 0.4 hierro y cobre, 0.5 oro). Se confirmaron: Nether Gold Ore = 1 polvo, Nether Quartz Ore = 3 cuarzo,
Amethyst Cluster = 8, mena = 3, deepslate = 4.

## Correccion respecto a la primera entrega
- **Ancient Debris usa Blaze Powder, NO Fire Charge**, y es una receta **con forma**: fila `[Ancient Debris][Blaze Powder][Crushing Hammer]`
  (tambien valida en espejo). El Fire Charge fue una suposicion mia sobre una captura borrosa; el original lo desmiente.

## Anadido a partir del jar
- Mixin que impide fusionar dos martillos en la mesa de crafteo (reparacion vanilla): el original hace lo mismo.
- Tooltip de 3 lineas, etiquetas `c:dusts` y `c:dusts/<metal>`.
- Compat opcional con `fabric:all_mods_loaded`: Create (zinc), Create Ironworks (estano), Create More Additions (plata), AE2 (5 recetas).

## Aun sin verificar (no se puede sin compilar/ejecutar 26.3)
- **Firmas de Java en 26.3** de `CrushingRecipe` (`matches`, `assemble`, `getRemainingItems`, `placementInfo`) y del mixin
  (`RepairItemRecipe#matches`). Reescrito segun docs de Fabric 26.1.2; 26.3 movio las recetas a registros recargables.
- **Formato de `fabric:load_conditions` en 26.3** (copiado de 1.20.1). Si cambio, las recetas compat daran error en el log al faltar el mod, sin romper el resto.
- **IDs de los mods compat en 26.3** (Create, Ironworks, CMA, AE2): vienen de 1.20.1 y pueden haber cambiado.
- **Mekanism** (4 recetas `mekanism:enriching`): no portadas; dependen del formato de recetas de Mekanism en 26.3.

## Tension con la especificacion
Pediste "no superar Fortuna II", pero el original da 4 polvos por mena de deepslate. Se siguio el original.
