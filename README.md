# Vanilla 3D+ — Minecraft 26.2

Client-side Fabric visual mod. The 107.1 resource pack is embedded in the mod and registered as always enabled.

## Target environment
- Minecraft: 26.2
- Fabric Loader: 0.19.5
- Fabric API: 0.160.0+26.2
- Java: 25

## Build
This project intentionally does not vendor Fabric/Loom binaries. Use Gradle 9.5.1+ and the configured Fabric Loom 1.17.17, then run:

```bash
gradle build
```

The resulting JAR is in `build/libs/`.

## Included visual layer
- embedded Vanilla 3D+ resource pack (pack format 107.1)
- subtle block relief
- rain ambience and splashes
- fall-height-dependent water impacts
- ground dust and snow particles
- landing impact particles
- atmospheric weather particles
- world-space-style rainbow particle arc during clearing daylight
- restrained metallic light-catching particles for metal tools/weapons
- wet/weather ambience
- spear wind/impact particles
- mace impact particles scaled by fall/impact strength

The implementation is client-only and uses vanilla particle systems so it does not require server-side changes.
