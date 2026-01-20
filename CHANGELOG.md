# Changelog

## [9.19.0](https://github.com/cheminfo/openchemlib-js/compare/v9.18.2...v9.19.0) (2026-01-20)


### Features

* add changeCustomLabelPosition method on Molecule instance ([#358](https://github.com/cheminfo/openchemlib-js/issues/358)) ([71516e2](https://github.com/cheminfo/openchemlib-js/commit/71516e288fc0ff2d5f71c407a8225fd0db973e72))
* update OCL to v2026.1.2 + fix ([1ab2e53](https://github.com/cheminfo/openchemlib-js/commit/1ab2e53e0686c85e4c551e8e5882f8d109936591))

## [9.18.2](https://github.com/cheminfo/openchemlib-js/compare/v9.18.1...v9.18.2) (2025-11-25)


### Bug Fixes

* update OCL to v2025.11.2 ([#353](https://github.com/cheminfo/openchemlib-js/issues/353)) ([c429f45](https://github.com/cheminfo/openchemlib-js/commit/c429f45844b9b68abe8b56a42005ad5817322cc9))

## [9.18.1](https://github.com/cheminfo/openchemlib-js/compare/v9.18.0...v9.18.1) (2025-11-19)


### Bug Fixes

* **ts:** make library work with TypeScript's CommonJS resolution ([#354](https://github.com/cheminfo/openchemlib-js/issues/354)) ([eba54de](https://github.com/cheminfo/openchemlib-js/commit/eba54de24826f53a7827a8edbc0c80b3b7c234ef))

## [9.18.0](https://github.com/cheminfo/openchemlib-js/compare/v9.17.0...v9.18.0) (2025-11-12)


### Features

* add toRxn options ([#350](https://github.com/cheminfo/openchemlib-js/issues/350)) ([b947af8](https://github.com/cheminfo/openchemlib-js/commit/b947af8cafbde0dfeee5e1c623dbfe156ce3b92f))

## [9.17.0](https://github.com/cheminfo/openchemlib-js/compare/v9.16.1...v9.17.0) (2025-11-06)


### Features

* **canvas_editor:** allow to submit dialog forms ([aeb6fa2](https://github.com/cheminfo/openchemlib-js/commit/aeb6fa27297a9a02a004db19073e916e32f2f7db))


### Bug Fixes

* **canvas_editor:** use asynchronous dialog callbacks ([839f972](https://github.com/cheminfo/openchemlib-js/commit/839f972da6bd6080919c88bb1040748810b00d6a))

## [9.16.1](https://github.com/cheminfo/openchemlib-js/compare/v9.16.0...v9.16.1) (2025-11-05)


### Bug Fixes

* update OCL to fix custom label change event ([4ab0fdd](https://github.com/cheminfo/openchemlib-js/commit/4ab0fddf60ea65df742c882f725b2b45317e2fb0))
* update OCL to fix ReactionEncoder with only reactants ([f88874f](https://github.com/cheminfo/openchemlib-js/commit/f88874f6564a45724d24eb4824ac4db2d3b0b194))

## [9.16.0](https://github.com/cheminfo/openchemlib-js/compare/v9.15.0...v9.16.0) (2025-10-31)


### Features

* **svg:** add `noAtomCustomLabels` depictor option ([#342](https://github.com/cheminfo/openchemlib-js/issues/342)) ([56125d5](https://github.com/cheminfo/openchemlib-js/commit/56125d5babecd841c3325204d0f4f8319f6d4adf))


### Bug Fixes

* **svg:** avoid stereo problems on nitrogen ([#341](https://github.com/cheminfo/openchemlib-js/issues/341)) ([5c2b9f3](https://github.com/cheminfo/openchemlib-js/commit/5c2b9f327e0389fea6c8c72fb366ee21bed1355d))

## [9.15.0](https://github.com/cheminfo/openchemlib-js/compare/v9.14.1...v9.15.0) (2025-10-31)


### Features

* improve handling of SVG depictor options ([#336](https://github.com/cheminfo/openchemlib-js/issues/336)) ([f08c5f6](https://github.com/cheminfo/openchemlib-js/commit/f08c5f6210a63aceb1e8e92397ff12cb4958c659))
* update OCL to master 2025-10-31 ([#338](https://github.com/cheminfo/openchemlib-js/issues/338)) ([2d55424](https://github.com/cheminfo/openchemlib-js/commit/2d55424bcb9cdb32f7c75cf080cb2e7b88249700))

## [9.14.1](https://github.com/cheminfo/openchemlib-js/compare/v9.14.0...v9.14.1) (2025-10-29)


### Bug Fixes

* `noCarbonLabelWithCustomLabel` depictor option ([#333](https://github.com/cheminfo/openchemlib-js/issues/333)) ([177b669](https://github.com/cheminfo/openchemlib-js/commit/177b6696c15aa6de817b5dd01ff10cb7b60a5610))

## [9.14.0](https://github.com/cheminfo/openchemlib-js/compare/v9.13.0...v9.14.0) (2025-10-29)


### Features

* add `noCarbonLabelWithCustomLabel` depictor option ([965db76](https://github.com/cheminfo/openchemlib-js/commit/965db76d804aba64362f2ae5068109dcbb91fddd))
* update OCL to v2025.10.3 ([759e826](https://github.com/cheminfo/openchemlib-js/commit/759e826316f0aed13b13049995ffe8c7df34ced5))

## [9.13.0](https://github.com/cheminfo/openchemlib-js/compare/v9.12.1...v9.13.0) (2025-10-28)


### Features

* convert ZZC molfile lines to atomCustomLabels ([#328](https://github.com/cheminfo/openchemlib-js/issues/328)) ([aa214e3](https://github.com/cheminfo/openchemlib-js/commit/aa214e36a886a7357574eaaa3831832b1b750d93))

## [9.12.1](https://github.com/cheminfo/openchemlib-js/compare/v9.12.0...v9.12.1) (2025-10-22)


### Bug Fixes

* toMolfile and fromMolfile deals with CR LF or LF correctly with custom atom labels ([eb0c9a9](https://github.com/cheminfo/openchemlib-js/commit/eb0c9a9a7184bf0266ac0a49ef828f20e301cf0d))

## [9.12.0](https://github.com/cheminfo/openchemlib-js/compare/v9.11.1...v9.12.0) (2025-10-15)


### Features

* add getMatchList and findFragmentInMolecule on SSSearcher ([#319](https://github.com/cheminfo/openchemlib-js/issues/319)) ([7d90ec2](https://github.com/cheminfo/openchemlib-js/commit/7d90ec24e26b6c0e36ce10145d6c38ea6ff789c9))

## [9.11.1](https://github.com/cheminfo/openchemlib-js/compare/v9.11.0...v9.11.1) (2025-10-14)


### Bug Fixes

* toMolfile was not taking into account the customLabelPosition option ([3452bc1](https://github.com/cheminfo/openchemlib-js/commit/3452bc178dcd9ed079413dbbf5faef85a71e6ffc))

## [9.11.0](https://github.com/cheminfo/openchemlib-js/compare/v9.10.0...v9.11.0) (2025-10-13)


### Features

* add Molecule.prototype.getNextCustomAtomLabel ([#317](https://github.com/cheminfo/openchemlib-js/issues/317)) ([d0050d1](https://github.com/cheminfo/openchemlib-js/commit/d0050d14c435c70a6ad04629e610f1326e90ea1f))

## [9.10.0](https://github.com/cheminfo/openchemlib-js/compare/v9.9.0...v9.10.0) (2025-10-08)


### Features

* add option to remove custom labels in molfile v2000 ([#315](https://github.com/cheminfo/openchemlib-js/issues/315)) ([91d5f70](https://github.com/cheminfo/openchemlib-js/commit/91d5f70504862cd24ba846c78752115bb04876a9))

## [9.9.0](https://github.com/cheminfo/openchemlib-js/compare/v9.8.0...v9.9.0) (2025-10-07)


### Features

* add options to fromMolfile and toMolfile ([#313](https://github.com/cheminfo/openchemlib-js/issues/313)) ([ec1a7b6](https://github.com/cheminfo/openchemlib-js/commit/ec1a7b6a20deaf9d8bdfaed0a6d9deeaacbb75a8))

## [9.8.0](https://github.com/cheminfo/openchemlib-js/compare/v9.7.0...v9.8.0) (2025-09-15)


### Features

* update OCL to v2025.9.1 and deprecate `toSmiles` ([#312](https://github.com/cheminfo/openchemlib-js/issues/312)) ([48a6d8f](https://github.com/cheminfo/openchemlib-js/commit/48a6d8f3019bb2594dabd0d107c962e8aa3dffdd))


### Bug Fixes

* **types:** get/setAtomCustomLabel support `null` ([412363d](https://github.com/cheminfo/openchemlib-js/commit/412363daaba2db54b410015cab81ff234e097dd7))

## [9.7.0](https://github.com/cheminfo/openchemlib-js/compare/v9.6.0...v9.7.0) (2025-08-13)


### Features

* update OCL to v2025.8.1 ([1dac22c](https://github.com/cheminfo/openchemlib-js/commit/1dac22cde45ae360a1f6ce58f89273e90615dee0))

## [9.6.0](https://github.com/cheminfo/openchemlib-js/compare/v9.5.1...v9.6.0) (2025-07-14)


### Features

* allow to identify root div of CanvasEditor ([#305](https://github.com/cheminfo/openchemlib-js/issues/305)) ([aadd46f](https://github.com/cheminfo/openchemlib-js/commit/aadd46f0c964e739501bc6102b34923988a8a727))

## [9.5.1](https://github.com/cheminfo/openchemlib-js/compare/v9.5.0...v9.5.1) (2025-07-14)


### Bug Fixes

* update OCL to 2025.7.1 + editor fix ([#303](https://github.com/cheminfo/openchemlib-js/issues/303)) ([1f84edf](https://github.com/cheminfo/openchemlib-js/commit/1f84edf4c19efe9046ef9900197bc6a9d3ec5f83))

## [9.5.0](https://github.com/cheminfo/openchemlib-js/compare/v9.4.1...v9.5.0) (2025-07-11)


### Features

* add `Molecule.fromText` method and support pasting in the editor ([#301](https://github.com/cheminfo/openchemlib-js/issues/301)) ([00322ad](https://github.com/cheminfo/openchemlib-js/commit/00322ad4a3d80392578ed1ac8ca0569cee96d536))

## [9.4.1](https://github.com/cheminfo/openchemlib-js/compare/v9.4.0...v9.4.1) (2025-07-11)


### Bug Fixes

* do not throw when molecule.toSVG is called with zero size ([#299](https://github.com/cheminfo/openchemlib-js/issues/299)) ([2b22ab7](https://github.com/cheminfo/openchemlib-js/commit/2b22ab74701b633f72259d6562fc07f3c1911902))

## [9.4.0](https://github.com/cheminfo/openchemlib-js/compare/v9.3.0...v9.4.0) (2025-07-10)


### Features

* update OCL to v2025.6.3 ([#296](https://github.com/cheminfo/openchemlib-js/issues/296)) ([533ed5d](https://github.com/cheminfo/openchemlib-js/commit/533ed5db9bc5d49194885ff0b27e92b124bb541e))


### Bug Fixes

* **canvas_editor:** do not repaint editor when its size is zero ([#298](https://github.com/cheminfo/openchemlib-js/issues/298)) ([a8d8805](https://github.com/cheminfo/openchemlib-js/commit/a8d8805e07ccd18f2ad10a91394cbe7ebd08a3ca))

## [9.3.0](https://github.com/cheminfo/openchemlib-js/compare/v9.2.0...v9.3.0) (2025-06-17)


### Features

* update OCL to v2025.6.1 ([#295](https://github.com/cheminfo/openchemlib-js/issues/295)) ([343cf4b](https://github.com/cheminfo/openchemlib-js/commit/343cf4b8067ec60cb3c198280c3d14ca8592f9e3))


### Bug Fixes

* handle empty reactions in custom element ([082d1b8](https://github.com/cheminfo/openchemlib-js/commit/082d1b84497b305ca429e0e757dd13bda8e29a09))
* initialize custom element state on construct ([31c1a11](https://github.com/cheminfo/openchemlib-js/commit/31c1a1187937ef093d1e0b705502c33130683caa))

## [9.2.0](https://github.com/cheminfo/openchemlib-js/compare/v9.1.1...v9.2.0) (2025-05-13)


### Features

* implement and expose Canonizer ([#267](https://github.com/cheminfo/openchemlib-js/issues/267)) ([35b7705](https://github.com/cheminfo/openchemlib-js/commit/35b77053633c8751ac70e84321c279f95b5b8857))


### Bug Fixes

* use head prepend instead mutate possibly external stylesheet ([#290](https://github.com/cheminfo/openchemlib-js/issues/290)) ([f49456d](https://github.com/cheminfo/openchemlib-js/commit/f49456df354bf515a3e022a22101804908baea7b))

## [9.1.1](https://github.com/cheminfo/openchemlib-js/compare/v9.1.0...v9.1.1) (2025-05-01)


### Bug Fixes

* **canvas_editor:** avoid blurry rendering on hdpi screens ([#281](https://github.com/cheminfo/openchemlib-js/issues/281)) ([efd4e5d](https://github.com/cheminfo/openchemlib-js/commit/efd4e5d11f92452bf300ea84746e279f5899ed0d))

## [9.1.0](https://github.com/cheminfo/openchemlib-js/compare/v9.0.1...v9.1.0) (2025-04-30)


### Features

* update OCL to v2025.4.3 ([#278](https://github.com/cheminfo/openchemlib-js/issues/278)) ([ce69044](https://github.com/cheminfo/openchemlib-js/commit/ce69044dc884ae2ff938c7a004d4c31c622973cb))


### Bug Fixes

* **canvas_editor:** open shadow root ([cb6c2d2](https://github.com/cheminfo/openchemlib-js/commit/cb6c2d22d4f3086fc5b11696c6e065ba45521164))
* **canvas_editor:** optimize draw context ([b7868f0](https://github.com/cheminfo/openchemlib-js/commit/b7868f0d2d72158d6fdd22017204056577e5dd57))

## [9.0.1](https://github.com/cheminfo/openchemlib-js/compare/v9.0.0...v9.0.1) (2025-04-28)


### Bug Fixes

* register resources from URL ([#275](https://github.com/cheminfo/openchemlib-js/issues/275)) ([342f038](https://github.com/cheminfo/openchemlib-js/commit/342f0386f5939b2f6165023ed939d9321a3cd97b))

## [9.0.0](https://github.com/cheminfo/openchemlib-js/compare/v8.21.0...v9.0.0) (2025-04-17)


### ⚠ BREAKING CHANGES

* `openchemlib` is now exported as an ESM-only package. This doesn't prevent using it from CommonJS (`require('openchemlib')`), as long as you are using a supported and up to date Node.js version.
* The library now exposes only one build. If you were using `openchemlib/minimal`, `openchemlib/core`, or `openchemlib/full`, switch back to `openchemlib`, which contains everything.
* Removed the following classes: `SVGRenderer`, `StructureEditor`, and `StructureView`. They were using old and deprecated OpenChemLib java APIs.
  * Instead of the `SVGRenderer`, `molecule.toSVG()` can be used.
  * Instead of the `StructureEditor`, the replacement `CanvasEditor` can be used.
  * Instead of the `StructureView`, the replacement `CanvasEditor` with the `readOnly` option can be used.
* Removed the bundled static resources. This allows to reduce the build size by half.
  * Static data can be loaded/registered with these new methods: `OCL.Resources.register`, `OCL.Resources.registerFromUrl`, or `OCL.Resources.registerFromNodejs`.
  * These classes require static resources to be registered before being instantiated: `ConformerGenerator`, `ForceFieldMMFF94`, `DruglikenessPredictor`, `ToxicityPredictor`.
* Removed support for Node.js 18, which will be EoL at the end of April 2025.
* Removed the `getOCL` method from all classes except `Molecule`.
* Renamed some TypeScript types for consistency.

### Features

* rework library packaging and remove deprecated editor ([#266](https://github.com/cheminfo/openchemlib-js/issues/266)) ([88cc001](https://github.com/cheminfo/openchemlib-js/commit/88cc0010f3d2f9000fd10d2b76b71101b8764160))

## [8.21.0](https://github.com/cheminfo/openchemlib-js/compare/v8.20.2...v8.21.0) (2025-04-11)


### Features

* **CanvasEditor:** add `isDestroyed` getter ([#264](https://github.com/cheminfo/openchemlib-js/issues/264)) ([37065c6](https://github.com/cheminfo/openchemlib-js/commit/37065c6e7af0232e405438646c457025c3a6cb94))

## [8.20.2](https://github.com/cheminfo/openchemlib-js/compare/v8.20.1...v8.20.2) (2025-04-11)


### Bug Fixes

* expose `moleculeChanged` method from Java ([#262](https://github.com/cheminfo/openchemlib-js/issues/262)) ([6f0057c](https://github.com/cheminfo/openchemlib-js/commit/6f0057ca196fcd66218f5870e0e2fdf5078b9d90))

## [8.20.1](https://github.com/cheminfo/openchemlib-js/compare/v8.20.0...v8.20.1) (2025-04-11)


### Bug Fixes

* typo in CanvasEditor removeOnChangeListener method ([#260](https://github.com/cheminfo/openchemlib-js/issues/260)) ([270731c](https://github.com/cheminfo/openchemlib-js/commit/270731c3f63bb231b7cb174fd20badc182de3195))

## [8.20.0](https://github.com/cheminfo/openchemlib-js/compare/v8.19.0...v8.20.0) (2025-04-10)


### Features

* update OCL to v2025.4.0 ([97ca346](https://github.com/cheminfo/openchemlib-js/commit/97ca346ad1fc9b07594358950f80982317d5ce89))


### Bug Fixes

* do not use unexposed Java API from JS ([#257](https://github.com/cheminfo/openchemlib-js/issues/257)) ([9af170b](https://github.com/cheminfo/openchemlib-js/commit/9af170b32139ac2423707a7c3b57cd872ba9f02f))

## [8.19.0](https://github.com/cheminfo/openchemlib-js/compare/v8.18.1...v8.19.0) (2025-03-14)


### Features

* **CanvasEditorElement:** expose the `moleculeChanged()` method ([#250](https://github.com/cheminfo/openchemlib-js/issues/250)) ([d4c1c13](https://github.com/cheminfo/openchemlib-js/commit/d4c1c1342575e92cc9448f8a2ab98834b37c0772))

## [8.18.1](https://github.com/cheminfo/openchemlib-js/compare/v8.18.0...v8.18.1) (2025-02-06)


### Bug Fixes

* update OCL to master branch ([#247](https://github.com/cheminfo/openchemlib-js/issues/247)) ([29e9f08](https://github.com/cheminfo/openchemlib-js/commit/29e9f087385e99140fd9fce7cf62435400f84ac8))

## [8.18.0](https://github.com/cheminfo/openchemlib-js/compare/v8.17.0...v8.18.0) (2025-02-05)


### Features

* update GWT to v2.12.1 ([588efac](https://github.com/cheminfo/openchemlib-js/commit/588efac77bc93f5ea5b8b9e7ca49a884edbf405f))
* update OCL to v2025.2.0 ([e821ecb](https://github.com/cheminfo/openchemlib-js/commit/e821ecb8150e37be12930287c87cda07c42d91ed))


### Bug Fixes

* **types:** declare ambient types correctly ([aea1332](https://github.com/cheminfo/openchemlib-js/commit/aea1332da06b8f4322332f656d18f57636de475d))

## [8.17.0](https://github.com/cheminfo/openchemlib-js/compare/v8.16.0...v8.17.0) (2024-11-06)


### Features

* also emit change events when in readonly mode ([#240](https://github.com/cheminfo/openchemlib-js/issues/240)) ([c4ea31c](https://github.com/cheminfo/openchemlib-js/commit/c4ea31c3cbf751c538c135695c566c34db419e8e))
* update OCL to v2024.10.2 ([#237](https://github.com/cheminfo/openchemlib-js/issues/237)) ([63f7771](https://github.com/cheminfo/openchemlib-js/commit/63f777125b15fb6962ffef4350c851b925a7ebd5))
* update OCL to v2024.11.1 ([#241](https://github.com/cheminfo/openchemlib-js/issues/241)) ([9cfc345](https://github.com/cheminfo/openchemlib-js/commit/9cfc3454640279ceead43860d6cc6535a6f10171))

## [8.16.0](https://github.com/cheminfo/openchemlib-js/compare/v8.15.0...v8.16.0) (2024-10-17)


### Features

* add options to `ReactionEncoder.decode` and `ReactionEncoder.decode` ([9716b26](https://github.com/cheminfo/openchemlib-js/commit/9716b26a6c4d6a3927c0c2d59c021f96559b668c))
* create a script to build help ([#223](https://github.com/cheminfo/openchemlib-js/issues/223)) ([cc7042f](https://github.com/cheminfo/openchemlib-js/commit/cc7042f13b41e2dcb8c347ada9c28b2dd919e860))
* sync webcomponent props with attributes ([#234](https://github.com/cheminfo/openchemlib-js/issues/234)) ([9716b26](https://github.com/cheminfo/openchemlib-js/commit/9716b26a6c4d6a3927c0c2d59c021f96559b668c))
* update OCL to v2024.10.0 ([2cee300](https://github.com/cheminfo/openchemlib-js/commit/2cee300f4b19aed035f3c79beda8abf1ea4f8888))
* update OCL to v2024.10.1 ([#233](https://github.com/cheminfo/openchemlib-js/issues/233)) ([5eb0ed8](https://github.com/cheminfo/openchemlib-js/commit/5eb0ed8a4a107a98d70056452e52737b4a441507))


### Bug Fixes

* double click event handling ([#232](https://github.com/cheminfo/openchemlib-js/issues/232)) ([8cb438b](https://github.com/cheminfo/openchemlib-js/commit/8cb438b15f0f4b82e33f1fa8e302964929ae1f2f))

## [8.15.0](https://github.com/cheminfo/openchemlib-js/compare/v8.14.0...v8.15.0) (2024-07-25)


### Features

* implement generic editor ([#219](https://github.com/cheminfo/openchemlib-js/issues/219)) ([8a8185f](https://github.com/cheminfo/openchemlib-js/commit/8a8185f7f32f9c99ede783a8d3fcc5bc6882f638))


### Bug Fixes

* hydrogen highlight reset ([f0de006](https://github.com/cheminfo/openchemlib-js/commit/f0de006015e082259033134ba5962e8bfcbcac88))
* update OCL to 2024.7.1 ([4d6b4fb](https://github.com/cheminfo/openchemlib-js/commit/4d6b4fb4d0f47b379aaaf31720e0b6f7e028d5b9))

## [8.14.0](https://github.com/cheminfo/openchemlib-js/compare/v8.13.0...v8.14.0) (2024-06-19)


### Features

* add setAtomQueryFeature ([#213](https://github.com/cheminfo/openchemlib-js/issues/213)) ([7136cd9](https://github.com/cheminfo/openchemlib-js/commit/7136cd98f4752a633eeba2ea352ea1193c075a5b))

## [8.13.0](https://github.com/cheminfo/openchemlib-js/compare/v8.12.0...v8.13.0) (2024-05-28)


### Features

* add molecule.getBondQueryFeaturesObject ([#210](https://github.com/cheminfo/openchemlib-js/issues/210)) ([2844831](https://github.com/cheminfo/openchemlib-js/commit/2844831650d8b76442d00effbcd7ed93a66b1d18))

## [8.12.0](https://github.com/cheminfo/openchemlib-js/compare/v8.11.0...v8.12.0) (2024-05-27)


### Features

* add method Molecule.getAtomQueryFeaturesObject ([c1843b8](https://github.com/cheminfo/openchemlib-js/commit/c1843b8738eb8c87d12997dd0be7b9cec265b75c))

## [8.11.0](https://github.com/cheminfo/openchemlib-js/compare/v8.10.0...v8.11.0) (2024-05-17)


### Features

* add experimental support for touch events in editor ([#206](https://github.com/cheminfo/openchemlib-js/issues/206)) ([bc41dcc](https://github.com/cheminfo/openchemlib-js/commit/bc41dccbaddeedce1105046f796859d32e94ad84))

## [8.10.0](https://github.com/cheminfo/openchemlib-js/compare/v8.9.0...v8.10.0) (2024-05-13)


### Features

* add possitility to generate smarts and kekule isomeric smiles ([#204](https://github.com/cheminfo/openchemlib-js/issues/204)) ([0290193](https://github.com/cheminfo/openchemlib-js/commit/0290193bde842c1be2cd4661c138906476b549b8))
* update OCL to 2024.5.0 ([#203](https://github.com/cheminfo/openchemlib-js/issues/203)) ([802d12d](https://github.com/cheminfo/openchemlib-js/commit/802d12da550d8fd482cb4feb8245d2667b32730d))
* update OCL to v2024.4.0 ([#197](https://github.com/cheminfo/openchemlib-js/issues/197)) ([70edd49](https://github.com/cheminfo/openchemlib-js/commit/70edd49cc5b7d45ac0e7060ad81f3281fdc4cef4))


### Bug Fixes

* stop propagation of key events ([#198](https://github.com/cheminfo/openchemlib-js/issues/198)) ([bd21926](https://github.com/cheminfo/openchemlib-js/commit/bd219261daa664b30b1b327bb6884be099d43d4d))

## [8.9.0](https://github.com/cheminfo/openchemlib-js/compare/v8.8.1...v8.9.0) (2024-02-22)


### Features

* update OCL to v2024.2.1 ([afaae1e](https://github.com/cheminfo/openchemlib-js/commit/afaae1e5f69be99a96d83831cd9925db6796602f))


### Bug Fixes

* do not crash when drag-and-drop comes from toolbar ([#195](https://github.com/cheminfo/openchemlib-js/issues/195)) ([f10c782](https://github.com/cheminfo/openchemlib-js/commit/f10c782d1672770f636696549bd34daac087c088))

## [8.8.1](https://github.com/cheminfo/openchemlib-js/compare/v8.8.0...v8.8.1) (2024-02-01)


### Bug Fixes

* only add data-atom-map-no when defined ([#190](https://github.com/cheminfo/openchemlib-js/issues/190)) ([567b9d7](https://github.com/cheminfo/openchemlib-js/commit/567b9d765da1ef77bafeee61b49d685cf0f35055))

## [8.8.0](https://github.com/cheminfo/openchemlib-js/compare/v8.7.2...v8.8.0) (2024-01-19)


### Features

* update OCL to v2024.1.1 ([#186](https://github.com/cheminfo/openchemlib-js/issues/186)) ([3e8ac2e](https://github.com/cheminfo/openchemlib-js/commit/3e8ac2edad2f247e8d0f23c320791fd7d7b0b47b))

## [8.7.2](https://github.com/cheminfo/openchemlib-js/compare/v8.7.1...v8.7.2) (2023-11-14)


### Bug Fixes

* deal correctly with non matching reactions ([#181](https://github.com/cheminfo/openchemlib-js/issues/181)) ([74e337e](https://github.com/cheminfo/openchemlib-js/commit/74e337e3f8ed9722d27296b0be52ce651b3a1d89))

## [8.7.1](https://github.com/cheminfo/openchemlib-js/compare/v8.7.0...v8.7.1) (2023-10-16)


### Bug Fixes

* make named imports work from ESM ([#178](https://github.com/cheminfo/openchemlib-js/issues/178)) ([46a0237](https://github.com/cheminfo/openchemlib-js/commit/46a0237bbd3824d2834d986d9fc484bf82791dcb))

## [8.7.0](https://github.com/cheminfo/openchemlib-js/compare/v8.6.0...v8.7.0) (2023-10-13)


### Features

* improve exports for ESM and add shorthand export for the full.pretty build ([688aedd](https://github.com/cheminfo/openchemlib-js/commit/688aedd65c761ec28ef37a12c92c091f7591d371))

## [8.6.0](https://github.com/cheminfo/openchemlib-js/compare/v8.5.0...v8.6.0) (2023-10-06)


### Features

* when generating SVG check that coordinates are not all 0 ([#174](https://github.com/cheminfo/openchemlib-js/issues/174)) ([eded253](https://github.com/cheminfo/openchemlib-js/commit/eded25358626cadbeaa4c1796675e6fa525e0b28))

## [8.5.0](https://github.com/cheminfo/openchemlib-js/compare/v8.4.0...v8.5.0) (2023-08-14)


### Features

* add getFinalRanks ([#171](https://github.com/cheminfo/openchemlib-js/issues/171)) ([9f9974d](https://github.com/cheminfo/openchemlib-js/commit/9f9974d186765642cdc93dbd206f3b6e5ea29b52))

## [8.4.0](https://github.com/cheminfo/openchemlib-js/compare/v8.3.0...v8.4.0) (2023-08-11)


### Features

* add data-atom-map-no props in the circle of the svg ([#170](https://github.com/cheminfo/openchemlib-js/issues/170)) ([ea29871](https://github.com/cheminfo/openchemlib-js/commit/ea29871b5598419f2671ce1a095714387272d842))


### Bug Fixes

* CanonizerUtil with NOSTEREO yielded to a wrong result ([#165](https://github.com/cheminfo/openchemlib-js/issues/165)) ([817ec97](https://github.com/cheminfo/openchemlib-js/commit/817ec9789a604660641966936c3806dd00fc0d46))

## [8.3.0](https://github.com/cheminfo/openchemlib-js/compare/v8.2.0...v8.3.0) (2023-04-05)


### Features

* implement SmilesParser to allow SMARTS features ([#156](https://github.com/cheminfo/openchemlib-js/issues/156)) ([2ca12ed](https://github.com/cheminfo/openchemlib-js/commit/2ca12ed4183de911ec3ce3a4619db2cdeec77be9))
* update OCL to v2023.1.2 ([e134d00](https://github.com/cheminfo/openchemlib-js/commit/e134d000c5dbef79413e39cff2b209148e894349))
* update OCL to v2023.2.1 ([#160](https://github.com/cheminfo/openchemlib-js/issues/160)) ([9d2dd13](https://github.com/cheminfo/openchemlib-js/commit/9d2dd1357fe47db44d67b109632bd0404d31d07f))
* update OCL to v2023.4.0 ([a886ff4](https://github.com/cheminfo/openchemlib-js/commit/a886ff407d6d124f9d0dc8cb30856da9cc5fccda))

## [8.2.0](https://github.com/cheminfo/openchemlib-js/compare/v8.1.0...v8.2.0) (2023-01-25)


### Features

* add new CANONIZER static options ([bd2eca1](https://github.com/cheminfo/openchemlib-js/commit/bd2eca1356c995ba51d0ac46f23305038610d943))


### Bug Fixes

* add getAtomicNoFromLabel second argument ([8aa1268](https://github.com/cheminfo/openchemlib-js/commit/8aa12680634498b65e18f5172c7e16540ec4e463))
* correct and expose all static parameters of JSMolecule ([81d7361](https://github.com/cheminfo/openchemlib-js/commit/81d7361d6792c01ee3a9f36d01dd9bc41ab020ae))

## [8.1.0](https://github.com/cheminfo/openchemlib-js/compare/v8.0.1...v8.1.0) (2023-01-04)


### Features

* expose ReactionEncoder to encode / decode a reaction as a string ([0d645c3](https://github.com/cheminfo/openchemlib-js/commit/0d645c3a67f1386ca4b946af34bb35053eed2c20))
* expose Reactor ([5eb1c26](https://github.com/cheminfo/openchemlib-js/commit/5eb1c26cb9d17c3ecb3da610e063e0d5879162d8))
* expose Transformer ([e88d2b4](https://github.com/cheminfo/openchemlib-js/commit/e88d2b41f9c7db1e5fc983cfe78c9b60904232ee))
* update OCL to v2022.10.2 ([64b7d9a](https://github.com/cheminfo/openchemlib-js/commit/64b7d9a1a6cd261dbd64d24682ed2f3c9e3b88cd))
* update OCL to v2022.12.4 ([#146](https://github.com/cheminfo/openchemlib-js/issues/146)) ([8511629](https://github.com/cheminfo/openchemlib-js/commit/8511629bf3acbfbe48e8773134b7f9687de93465))

## [8.0.1](https://github.com/cheminfo/openchemlib-js/compare/v8.0.0...v8.0.1) (2022-08-15)


### Bug Fixes

* inverted red/blue colors in editor ([d8d25a3](https://github.com/cheminfo/openchemlib-js/commit/d8d25a36034baa52673cc589d9e684be423001ca))

## [8.0.0](https://github.com/cheminfo/openchemlib-js/compare/v7.5.0...v8.0.0) (2022-08-15)

### ⚠ BREAKING CHANGES

This release updates OpenChemLib to the latest version 2022.8.1. There were a lot
of changes since the last update and some of them are breaking. Here's a list of
the changes we were able to identify (but there may be more):

- The `setAtomQueryFeature` and `getAtomQueryFeatures` methods were removed from
  the `Molecule` class because they now return a `long` in Java.
- The `addRing`, `addRingToAtom` and `addRingToBond` methods of the `Molecule`
  class now take one additional parameter, called `bondLength`.
- The `showSymmetryDiastereotopic` and `showSymmetryEnantiotopic` depictor options
  were removed.
- The value of computed 3D coordinates may change.
- Generated conformers may be different (in quantity and their coordinates).
- It is no longer possible to change ring size in the atom query features dialog
  of the editor.

We also removed support for Node.js 12. It's still possible that the library works
on this version, but it is no longer tested in CI.

### Features

- update GWT to version 2.10 ([9eed1ef](https://github.com/cheminfo/openchemlib-js/commit/9eed1ef95255ae58232b267241f59feaacd517a7))
- update OpenChemLib to v2022.8.1 ([1ac3899](https://github.com/cheminfo/openchemlib-js/commit/1ac3899eb27b92692fe99d726a762cea4c5a7923))
- Added the following new depictor options (all boolean): `showSymmetryStereoHeterotopicity`,
  `showSymmetryAny`, `noColorOnESRAndCIP`, `noImplicitHydrogen`, and `drawBondsInGray`.

## [7.5.0](https://www.github.com/cheminfo/openchemlib-js/compare/v7.4.3...v7.5.0) (2022-08-09)

### Features

- expose CanonizerUtil ([#131](https://www.github.com/cheminfo/openchemlib-js/issues/131)) ([ef7a873](https://www.github.com/cheminfo/openchemlib-js/commit/ef7a87332e58272092276c4dbdcf6eca73b4c167))

### Bug Fixes

- README link to documentation ([77dfc46](https://www.github.com/cheminfo/openchemlib-js/commit/77dfc46130dab7a3ad3e85b2527986b78cde6cd8))
- **types:** coordinates can be omitted from `fromIDCode` ([636a73b](https://www.github.com/cheminfo/openchemlib-js/commit/636a73b129d637b538983ad4a9a6058a918b7660))

### [7.4.3](https://www.github.com/cheminfo/openchemlib-js/compare/v7.4.2...v7.4.3) (2021-10-02)

### Bug Fixes

- **fromIDCode:** pass coordinates option to the IDCodeParser ([0b8775e](https://www.github.com/cheminfo/openchemlib-js/commit/0b8775e656251e2ba172d991ce616b209875177e))

### [7.4.2](https://www.github.com/cheminfo/openchemlib-js/compare/v7.4.1...v7.4.2) (2021-08-05)

### Bug Fixes

- addImplicitHydrogens on 'Cl' ([f2cf71f](https://www.github.com/cheminfo/openchemlib-js/commit/f2cf71f3bb040a37b2bb2ec186c171df156631fb))

### [7.4.1](https://www.github.com/cheminfo/openchemlib-js/compare/v7.4.0...v7.4.1) (2021-07-27)

### Bug Fixes

- addImplicitHydrogens for NH3 ([9e9c353](https://www.github.com/cheminfo/openchemlib-js/commit/9e9c3539a479cc761dcdc69c19f63665ba810f99))
- publish projet on Zenodo ([e94ebb7](https://www.github.com/cheminfo/openchemlib-js/commit/e94ebb7a1036e54c8250aef1d9ae32ce413fd6e3))

## [7.4.0](https://www.github.com/cheminfo/openchemlib-js/compare/v7.3.0...v7.4.0) (2021-03-24)

### Features

- expose a "getOCL" method on public classes ([1879045](https://www.github.com/cheminfo/openchemlib-js/commit/18790459fada2a05e493f3392a41ef2d87101748))

## [7.3.0](https://www.github.com/cheminfo/openchemlib-js/compare/v7.2.3...v7.3.0) (2021-01-04)

### Features

- update OCL to version 2020.12.1 ([bb9c07d](https://www.github.com/cheminfo/openchemlib-js/commit/bb9c07dfa5e16632e6933ec3fd8eeeed0b0c59e0))

## 7.2.3 / 2020-01-10

- Update OCL to 2020.1.0
- Fix repaint issue in SVG Toolbar

## 7.2.2 / 2019-11-07

- Update OCL to 2019_11_05-15_48_20

## 7.2.1 / 2019-07-12

- Fix: work around a bug in Chromium with SVGDepictor when SVG is printed.

## 7.2.0 / 2019-07-08

- Add ConformerGenerator class.
- Add ForceFieldMMFF94 class.

## 7.1.1 / 2019-07-06

- Update OCL to 2019_07_04-04_00_27
- The SVG depictor (`Molecule#toSVG`) has been improved and its visual output is
  now more in line with the Java version.

## 7.1.0 / 2019-06-09

- Added two new options to the `toSVG` method:
  - `autoCrop`: if set to `true`, the SVG will be automatically crop to fit the bounds of the molecule.
    This will change the resulting dimensions of the SVG.
  - `autoCropMargin`: This is the margin (in px) kept around the molecule when `autoCrop` is `true`. Default: 5.
- Update OCL to 2019.6.0
- Added `is3D` option to the `removeExplicitHydrogens` method.

## 7.0.1 / 2019-05-05

- fix(types): make all depictor options effectively optional

## 7.0.0 / 2019-05-03

- Update OCL to 2019_05_03-16_18_03
- **BREAKING**: Node.js 6 is no longer supported
- **BREAKING**: The output of `Molecule.toSVG` has changed: bonds are now identified
  by the bond id instead of the ids of its two atoms. For example: `myId:Bond:0-1`
  becomes `myId:Bond:0`.

## 6.0.1 / 2019-02-08

- Update OCL to 2019_02_08-04_00_25
- Add missing return types in TypeScript declarations
- Support retina displays in StructureEditor

## 6.0.0 / 2019-01-27

- Update OCL to 2019_01_26-04_00_26
- **BREAKING**: Editor: the `StructureEditor` constructor now takes a DOM element instead of an id.

## 5.9.1 / 2018-12-21

- Update OCL to 2018_12_21-04_00_23
- Editor: fix bond query feature dialog

## 5.9.0 / 2018-12-17

- Update OCL to 2018_12_17-16_27_18
- Added RXN methods to Reaction class
- Added more options to bond query features in editor
- Added getBounds method to Molecule class

## 5.8.0 / 2018-12-09

- Update OCL to 2018_12_08-04_00_22
- Added Reaction class
- Added TypeScript declaration files
- Ported the documentation to TypeDoc

## 5.7.0 / 2018-12-02

- Update OCL to 2018.12.0
- Added support for copying and pasting molfiles
- Added molecule.toIsomericSmiles(includeAtomMapping)
- Fixed display of query features
- Fixed emitting change event for modifications coming from dialogs

## 5.6.1 / 2018-11-06

- Update OCL to 2018_11_03-04_00_20

## 5.6.0 / 2018-06-19

- Update OCL to 2018.6.0
- feat: expose RingCollection to JS

## 5.5.2 / 2018-04-27

- fix(editor): do not consider a mouse move for small movements

## 5.5.1 / 2018-04-19

- feat: add factorTextSize option to toSVG

## 5.5.0 / 2017-11-20

- Allow to retrieve a JSMolecule from editor

## 5.4.0 / 2017-11-16

- Update OCL to 2017_11_14-04_01_01

## 5.3.0 / 2017-09-22

- Update OCL to 2017_08_29-04_01_13
- Fix addMissingChirality

## 5.2.10 / 2017-07-13

- Fix H mapping issue

## 5.2.9 / 2017-07-07

- Use the X symbol for hydrogens in DiaID

## 5.2.8 / 2017-07-07

- Identical to 5.2.7

## 5.2.7 / 2017-07-07

- Use isotopes to mark atoms

## 5.2.6 / 2017-07-07

- Precalculate X atom number

## 5.2.5 / 2017-07-06

- Update to add missing stereochemistry based on parities helper

## 5.2.4 / 2017-07-06

- update diastereotopicAtomID not to rely on CIP

## 5.2.3 / 2017-07-04

- fix hoseCodeCreator

## 5.2.2 / 2017-07-03

- Update OCL to 2017_06_10-04_00_52
- Update diaID

## 5.2.1 / 2017-04-24

- Update OCL to 2017_04_22-04_01_31
- Fix a bug with the SMILES parser that could enter in a wrong state after parsing an invalid SMILES

## 5.2.0 / 2017-03-21

- feat: add fontWeight and strokeWidth options to toSVG

## 5.1.2 / 2017-03-08

- fix: Update OCL to 2017_03_03-04_00_41

## 5.1.1 / 2017-01-30

- fix: Update OCL to 2017_01_28-04_00_45

## 5.1.0 / 2017-01-27

- feat: add mol.toMolfileV3
- fix: Fixed SVGDepictor setLineWidth() regression.

## 5.0.0 / 2017-01-13

- remove viewer build
- introduce minimal build
- do not include MoleculeProperties in minimal build

BREAKING CHANGE:

`molecule.getProperties()` has been removed. `new MoleculeProperties(molecule)` must be used instead.

## 4.7.2 / 2017-01-10

- include full dist directory in npm and add require aliases

## 4.7.1 / 2017-01-10

- SVG depictor: compute string width without canvas for Helvetica. This allows the SVG depictor to be used on Node.js

## 4.7.0 / 2017-01-05

Update public methods of JSMolecule. This adds a few new methods:

- static double getDefaultAverageBondLength()
- static void setDefaultAverageBondLength(double defaultAVBL)
- int suggestBondType(int atom1, int atom2)
- double calculateTorsion(int[] atom)
- void setBondOrder(int bond,int order)
- int getMetalBondedConnAtoms(int atom)
- int getAllConnAtomsPlusMetalBonds(int atom)
- int getNonHydrogenNeighbourCount(int atom)
- int getExcludeGroupValence(int atom)
- int getLowestFreeValence(int atom)
- int getExplicitHydrogens(int atom)
- boolean isPseudoRotatableBond(int bond)
- boolean isAmideTypeBond(int bond)
- int getZNeighbour(int connAtom, int bond)
- int getHelperArrayStatus()

## 4.6.3 / 2017-01-04

- update OCL to 2017.1.0

## 4.6.2 / 2016-11-08

- AtomQueryFeaturesDialog: added more than 2 Hydrogens on query features
- Fix SVGDepictor: minimal bond width is now 1

## 4.6.1 / 2016-11-03

- fix license issue
- fix StructureEditor not present in last release

## 4.6.0 / 2016-10-25

- add more depictor options and support options in SVG depictor

## 4.5.1 / 2016-10-25

- update OCL to 2016_10_25-04_00_46

## 4.5.0 / 2016-10-19

- editor: fixed regression in chain placement
- update OCL to 2016_10_18-04_00_45

## 4.4.2 / 2016-09-23

- editor: fix initial placement of new bond/chain/ring
- editor: fix dialog positions

## 4.4.1 / 2016-09-23

- editor: fix structure change notification with keyboard
- editor: add drag and drop support
- editor: fix invasive paste issue

## 4.4.0 / 2016-09-01

- fix highlighting issues
- add SVG editor version
- add compatibility with CouchDB views

## 4.3.2 / 2016-08-16

- update openchemlib
- update GWT compiler

## 4.3.1 / 2016-07-19

- update openchemlib

## 4.3.0 / 2016-05-24

- update openchemlib
- add support for exclude group

## 4.2.2 / 2016-04-25

- update openchemlib
- remove sourceURL from dist files

## 4.2.1 / 2016-04-20

- toSVG now works correctly in core build
- warn that toSVG only works in a browser's window

## 4.2.0 / 2016-04-14

- add DrugScoreCalculator

## 4.1.0 / 2016-03-17

- add new contrib methods to generate HOSE codes and diastereotopic atom IDs
- do not use global window for `core` library (still needed for the other builds)
- this allows to load any number of independant versions of `core` along with one version of `full` or `viewer`
- `viewer` will probably be removed in the future, leaving only `core` and `full`

## 4.0.0 / 2016-02-16

- Major: use property getters for MolecularFormula and MoleculeProperties
- update openchemlib

## 3.2.2 / 2015-12-15

- fix namespace for editor and structure viewer

## 3.2.1 / 2015-12-15

- refactor for and recompile with GWT 2.8.0-beta1

## 3.2.0 / 2015-11-24

- add auto-generated methods to Molecule

## 3.1.1 / 2015-11-07

- Editor: fixed AtomHightlight
- Editor: working query feature dialogs

## 3.1.0 / 2015-10-21

- add ToxicityPredictor
- add DruglikenessPredictor

## 3.0.1 / 2015-10-20

- update openchemlib
- use GWT 2.8 to fix bug in SSSearcher

## 3.0.0 / 2015-09-28

- Make JS API in line with Java API
- Remove cheminfo-specific methods

## 3.0.0-beta6 / 2015-06-03

- Changed prefix from actchem to OCL

## 3.0.0-beta5 / 2015-06-01

- Fixed resizing issues in StructureViewer

## 3.0.0-beta4 / 2015-05-15

- fix layout and missing images in editor

## 3.0.0-beta3 / 2015-05-12

- fix resizing
- fix toolbar in case of multiple instances
- fix right click issues

## 3.0.0-beta2 / 2015-05-11

- build without fake window

## 3.0.0-beta1 / 2015-05-11

- update openchemlib
- build editor

## 3.0.0-alpha10 / 2015-04-08

- update openchemlib

## 3.0.0-alpha9 / 2015-03-20

- fix SVG depictor

## 3.0.0-alpha8 / 2015-03-20

- add bower.json

## 3.0.0-alpha7 / 2015-03-20

- update SVGDepictor
- expose molecule.getDiastereotopicAtomIDsArray

## 3.0.0-alpha6 / 2015-03-18

- fixed build errors for editor (missing class): this prevented exports
- add SVG output

## 3.0.0-alpha5 / 2015-02-27

- update chemlib
- update gwt exporter (no more globals)
- add options to Molecule.fromSmiles

## 3.0.0-alpha4 / 2015-02-06

- update chemlib
- fix SMILES parser

## 3.0.0-alpha3 / 2015-01-26

- update chemlib
- Depictor : new flag to allow suppression of stereo problems
- Depictor : fix superscript locations
- SMILES parser : @ indicators in smiles don't conflict anymore with implicit hydrogens as in [N@H]
- SMILES parser : handling of '.' was improved
- SMILES parser : detection of unusual valences was improved
- add noStereoProblem option to drawStructure

## 3.0.0-alpha2 / 2015-01-23

- add support for displayMode options in javascript

## 3.0.0-alpha1 / 2015-01-22

- add StructureViewer

## 2.0.7 / 2015-01-21

- ensure invented coordinates are always the same

## 2.0.6 / 2015-01-19

- update java library

## 2.0.5 / 2015-01-15

- SMILES parser : custom coordinate inventor
- update java library

## 2.0.4 / 2015-01-12

- Update java library

## 2.0.3 / 2015-01-09

- Github webhook

## 2.0.2 / 2014-12-22

- fix bugs in SMILES parser

## 2.0.1 / 2014-12-19

- add molecule.ensureHelperArrays

## 2.0.0 / 2014-12-05

- GWT 2.7
- no more javascript getters
- initialize molecule with 32 length arrays
- improve IDCode parser performance

## 1.2.1 / 2014-11-18

- add molecule.getIDCodeAndCoordinates()
- fix bug with SSSearchWithIndex

## 1.2.0 / 2014-11-11

- add dependency to actelion-ext project
- add molecule.getIDCoordinates()
- add Molecule.fromIDCode(idcode, coordinates)
- add Molecule.expandHydrogens()
- add Molecule.getDiastereotopicAtomIDs
- perf improvement : parsers and other utility classes are created only once and when needed

## 1.1.0 / 2014-11-05

- add molecule.getFragmentNumbers() and molecule.getFragments()

## 1.0.0 / 2014-09-10

- first release
