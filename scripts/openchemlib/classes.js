import { generateImageData } from './generateImageData.js';
import { removedClasses } from './removed.js';

const modifiedClasses = [
  'chem/io/Mol2FileParser',
  'chem/io/ODEFileParser',
  'gui/hidpi/HiDPIHelper',
  'gui/hidpi/HiDPIIcon',
  'util/ConstantsDWAR',
  '@smile/clustering/KMeans',
];

export const modified = modifiedClasses.map(getFilename);

const changedClasses = [
  ['@org/openmolecules/chem/conf/gen/BaseConformer', changeBaseConformer],
  [
    '@org/openmolecules/chem/conf/gen/ConformerGenerator',
    changeConformerGenerator,
  ],
  [
    '@org/openmolecules/chem/conf/so/SelfOrganizedConformer',
    changeSelfOrganizedConformer,
  ],
  [
    '@org/openmolecules/chem/conf/gen/RigidFragmentCache',
    changeRigidFragmentCache,
  ],
  ['chem/conf/TorsionDB', changeTorsionDB],
  [
    'chem/conf/torsionstrain/StatisticalTorsionPotential',
    changeStatisticalTorsionPotential,
  ],
  ['chem/Coordinates', removeToStringSpaceDelimited],
  ['chem/coords/InventorFragment', changeInventorFragment],
  ['chem/descriptor/FingerPrintGenerator', changeFingerPrintGenerator],
  [
    'chem/descriptor/flexophore/calculator/StructureCalculator',
    changeStructureCalculator,
  ],
  [
    'chem/descriptor/pharmacophoretree/HungarianAlgorithm',
    changeHungarianAlgorithm,
  ],
  [
    'chem/descriptor/pharmacophoretree/PharmacophoreTree',
    changePharmacophoreTree,
  ],
  ['chem/forcefield/mmff/Csv', changeCsv],
  ['chem/forcefield/mmff/Separation', replaceHashTable],
  ['chem/forcefield/mmff/Vector3', changeVector3],
  [
    'chem/interactionstatistics/InteractionDistanceStatistics',
    changeInteractionDistanceStatistics,
  ],
  ['chem/io/CompoundFileHelper', fixCompoundFileHelper],
  ['chem/io/pdb/converter/BondOrderCalculator', changeBondOrderCalculator],
  ['chem/io/pdb/converter/BondsCalculator', changeBondsCalculator],
  ['chem/io/RXNFileParser', replaceStandardCharsets(2)],
  ['chem/io/RXNFileV3Creator', removeRXNStringFormat],
  ['chem/io/SDFileParser', replaceStandardCharsets(2)],
  ['chem/Molecule', changeMolecule],
  ['chem/MolfileParser', replaceStandardCharsets(1)],
  ['chem/Molecule3D', removeCloneInfos],
  ['chem/PeriodicTable', replaceHashTable],
  ['chem/phesaflex/EvaluableFlexibleOverlap', changeEvaluableFlexibleOverlap],
  ['chem/prediction/IncrementTable', changeIncrementTable],
  ['chem/prediction/IncrementTableWithIndex', changeIncrementTableWithIndex],
  ['chem/prediction/ToxicityPredictor', changeToxicityPredictor],
  ['chem/reaction/ClassificationData', changeClassificationData],
  ['chem/reaction/mapping/RootAtomPairSource', changeRootAtomPairSource],
  ['chem/reaction/mapping/ReactionCenterMapper', changeReactionCenterMapper],
  ['chem/reaction/RSSSearcher', changeRSSSearcher],
  ['chem/TautomerHelper', changeTautomerHelper],
  ['chem/TextDrawingObject', changeTextDrawingObject],
  ['gui/editor/GenericEditorArea', changeGenericEditorArea],
  ['gui/editor/CustomAtomDialogBuilder', changeCustomAtomDialogBuilder],
  ['gui/generic/GenericDialog', changeGenericDialog],
  ['util/ArrayUtils', changeArrayUtils],
  ['util/datamodel/ByteVec', changeByteVec],
  ['util/StringFunctions', changeStringFunctions],
];

export const changed = changedClasses.map(([path, ...transformers]) => {
  return [getFilename(path), transformers];
});

export const removed = removedClasses.map(getFolderName);

const generatedClasses = [
  ['@gwt/js/api/generic/internal/ImageData', generateImageData],
];

export const generated = generatedClasses.map((file) => [
  getFilename(file[0]),
  file[1],
]);

function getFilename(file) {
  if (file.startsWith('@info/')) {
    return `../info/${file.replace('@info/', '')}.java`;
  }
  if (file.startsWith('@org/')) {
    return `../org/${file.replace('@org/', '')}.java`;
  }
  if (file.startsWith('@smile/')) {
    return `../smile/${file.replace('@smile/', '')}.java`;
  }
  if (file.startsWith('@gwt/')) {
    return `../../${file.replace('@gwt/', '')}.java`;
  } else {
    return `actelion/research/${file}.java`;
  }
}

function getFolderName(file) {
  if (file.startsWith('@org/')) {
    return `../org/${file.replace('@org/', '')}`;
  } else if (file.startsWith('@info/')) {
    return `../info/${file.replace('@info/', '')}`;
  } else {
    return `actelion/research/${file}`;
  }
}

function methodRegExp(methodName, options = {}) {
  const { indent = '\t' } = options;
  return new RegExp(
    `(?:public|private|protected).*? ${methodName}\\(.*\n(?:.*\n)*?${indent}}`,
    'g',
  );
}

function changeTorsionDB(code) {
  code = replaceChecked(
    code,
    'import java.io.*;',
    'import java.io.*;\nimport org.cheminfo.utils.FakeFileInputStream;',
  );
  code = replaceChecked(
    code,
    'TorsionDB.class.getResourceAsStream(',
    'FakeFileInputStream.getResourceAsStream(',
    2,
  );
  return code;
}

function changeStatisticalTorsionPotential(code) {
  code = replaceChecked(
    code,
    'import java.io.InputStreamReader;',
    'import java.io.InputStreamReader;\nimport org.cheminfo.utils.FakeFileInputStream;',
  );
  code = replaceChecked(
    code,
    'TorsionDB.class.getResourceAsStream(',
    'FakeFileInputStream.getResourceAsStream(',
    2,
  );
  return code;
}

function removeToStringSpaceDelimited(code) {
  return code.replaceAll(methodRegExp('toStringSpaceDelimited'), '');
}

function replaceChecked(code, from, to, times = 1) {
  for (let i = 0; i < times; i++) {
    if (!code.includes(from)) {
      throw new Error(`Cannot find ${from} in code (iteration: ${i}}`);
    }
    code = code.replace(from, to);
  }
  return code;
}

function removeSlice(code, start, end) {
  const startIdx = code.indexOf(start);
  if (startIdx === -1) {
    throw new Error(`did not find index for: ${start}`);
  }
  const endIdx = code.indexOf(end, startIdx + start.length);
  if (endIdx === -1) {
    throw new Error(`did not find index for: ${end}`);
  }
  return code.slice(0, startIdx) + code.slice(endIdx + end.length);
}

function changeInventorFragment(code) {
  return replaceChecked(
    code,
    'mGlobalAtom.clone();',
    'Arrays.copyOf(mGlobalAtom, mGlobalAtom.length);',
  );
}

function changeFingerPrintGenerator(code) {
  code = code.replace(methodRegExp('main', { indent: '    ' }), '');
  code = replaceChecked(
    code,
    'import java.util.Hashtable',
    'import org.cheminfo.utils.JSHashMap',
  );
  code = replaceChecked(code, 'Hashtable', 'JSHashMap', 2);
  return code;
}

function changeStructureCalculator(code) {
  code = replaceChecked(
    code,
    'match.clone()',
    'Arrays.copyOf(match, match.length)',
  );
  return code;
}

function changeHungarianAlgorithm(code) {
  code = removeSlice(
    code,
    'public static int readInput(String prompt)',
    'System.out.print("\\nTotal time required: " + timeElapsed + "\\n\\n");\n\t\t}',
  );
  return code;
}

function changePharmacophoreTree(code) {
  code = replaceChecked(
    code,
    'previousCut.clone()',
    'Arrays.copyOf(previousCut, previousCut.length)',
  );
  return code;
}

function removeCloneInfos(code) {
  return replaceChecked(
    code,
    'infos[a] = m.infos[i].clone();',
    '// infos[a] = m.infos[i].clone();',
  );
}

function changeEvaluableFlexibleOverlap(code) {
  code = replaceChecked(
    code,
    '@Override\n\tpublic EvaluableFlexibleOverlap clone',
    'public EvaluableFlexibleOverlap clone',
  );
  return code;
}

function changeIncrementTable(code) {
  code = replaceChecked(
    code,
    'import java.io.*;',
    'import java.io.*;\nimport org.cheminfo.utils.FakeFileInputStream;',
  );
  code = replaceChecked(
    code,
    'this.getClass().getResourceAsStream(',
    'FakeFileInputStream.getResourceAsStream(',
  );
  return code;
}

function changeIncrementTableWithIndex(code) {
  code = replaceChecked(
    code,
    'import java.io.BufferedReader;',
    'import org.cheminfo.utils.FakeFileInputStream;\nimport java.io.BufferedReader;',
  );
  code = replaceChecked(
    code,
    'this.getClass().getResourceAsStream(',
    'FakeFileInputStream.getResourceAsStream(',
  );
  return code;
}

function changeToxicityPredictor(code) {
  code = replaceChecked(
    code,
    'import java.io.IOException;',
    'import org.cheminfo.utils.FakeFileInputStream;\nimport java.io.IOException;',
  );
  code = replaceChecked(
    code,
    'this.getClass().getResourceAsStream(',
    'FakeFileInputStream.getResourceAsStream(',
    2,
  );
  return code;
}

function changeClassificationData(code) {
  code = code.replace(
    methodRegExp('initialize', { indent: '\t\t' }),
    'private void initialize() {}',
  );
  return code;
}

function changeRootAtomPairSource(code) {
  code = replaceChecked(
    code,
    'mReactantRank = mReactantCanonizer.getSymmetryRanks().clone();',
    'int[] reactantRank_ = mReactantCanonizer.getSymmetryRanks(); mReactantRank = Arrays.copyOf(reactantRank_, reactantRank_.length);',
  );
  code = replaceChecked(
    code,
    'mProductRank = mProductCanonizer.getSymmetryRanks().clone();',
    'int[] productRank_ = mProductCanonizer.getSymmetryRanks(); mProductRank = Arrays.copyOf(productRank_, productRank_.length);',
  );
  return code;
}

function changeReactionCenterMapper(code) {
  code = replaceChecked(
    code,
    'import java.util.ArrayList;',
    'import java.util.Arrays;\nimport java.util.ArrayList;',
  );
  code = replaceChecked(
    code,
    'mPermutationList.add(solution.clone());',
    'mPermutationList.add(Arrays.copyOf(solution, solution.length));',
  );
  return code;
}

function changeRSSSearcher(code) {
  code = replaceChecked(
    code,
    'import java.util.ArrayList;',
    'import java.util.Arrays;\nimport java.util.ArrayList;',
  );
  code = replaceChecked(code, 's.clone()', 'Arrays.copyOf(s, s.length)');
  code = replaceChecked(
    code,
    'System.out.printf(format,args);',
    'System.out.println("");',
    2,
  );
  return code;
}

function changeTautomerHelper(code) {
  code = replaceChecked(
    code,
    'import java.util',
    'import java.util.Arrays;\nimport java.util',
  );
  code = replaceChecked(
    code,
    'mRegionDCount.clone();',
    'Arrays.copyOf(mRegionDCount, mRegionDCount.length);',
  );
  code = replaceChecked(
    code,
    'mRegionTCount.clone();',
    'Arrays.copyOf(mRegionTCount, mRegionTCount.length);',
  );
  return code;
}

function changeTextDrawingObject(code) {
  code = replaceChecked(
    code,
    'import java.util.ArrayList;',
    'import java.math.BigDecimal;\nimport java.math.MathContext;\nimport java.util.ArrayList;',
  );
  return replaceChecked(
    code,
    String.raw`detail.append(String.format(" size=\"%.4f\"", mSize));`,
    String.raw`detail.append(" size=\""+new BigDecimal(mSize, new MathContext(4)).toString()+"\"");`,
  );
}

function changeGenericEditorArea(code) {
  // TODO: find replacements
  code = code.replaceAll(
    methodRegExp('showWarningMessage'),
    'private void showWarningMessage(String msg) {}',
  );
  code = code.replaceAll(
    methodRegExp('openReaction'),
    'private void openReaction() {}',
  );
  code = code.replaceAll('System.getProperty("development") != null', 'false');
  code = code.replace(
    'private void updateAndFireEvent(int updateMode)',
    'public void updateAndFireEvent(int updateMode)',
  );
  return code;
}

function changeCustomAtomDialogBuilder(code) {
  code = replaceChecked(code, 'e.getSource() instanceof JTextField', 'false');
  return code;
}

function changeGenericDialog(code) {
  code = replaceChecked(
    code,
    'import info.clearthought.layout.TableLayout;\n',
    '',
  );
  code = replaceChecked(
    code,
    'int PREFERRED = (int)TableLayout.PREFERRED;',
    'int PREFERRED = -2;',
  );
  code = replaceChecked(
    code,
    'int FILL = (int)TableLayout.FILL;',
    'int FILL = -1;',
  );
  return code;
}

function changeArrayUtils(code) {
  code = replaceChecked(
    code,
    'public class ArrayUtils {',
    `public class ArrayUtils {
\tpublic static int[] resize(int[] arr, int newSize) {
\t\tint[] newArray = new int[newSize];
\t\tint toCopy = Math.min(arr.length, newSize);
\t\tfor (int i = 0; i < toCopy; i++) {
\t\t\tnewArray[i] = arr[i];
\t\t}
\t\treturn newArray;
\t}`,
  );
  code = removeSlice(code, '\n	/**\n	 * Resize an array', 'return newArray;\n	}');
  code = removeSlice(code, '\n	/**\n	 * Copy an array ', 'return newArray;\n	}');
  return code;
}

function changeByteVec(code) {
  code = replaceChecked(code, 'doubleToRawLongBits', 'doubleToLongBits', 1);
  return code;
}

function changeStringFunctions(code) {
  code = removeSlice(
    code,
    '\n\tpublic static String toString(Exception ex) {',
    'exceptionAsString;\n\t}',
  );
  code = removeSlice(
    code,
    '\n\tpublic static String toStringStackTrace(Throwable ex){',
    'return sw.toString();\n\n\t}',
  );
  code = removeSlice(
    code,
    '\tpublic static String toString(Throwable ex) {',
    'return exceptionAsString;\n\t}',
  );
  return code;
}

function removeRXNStringFormat(code) {
  return replaceChecked(
    code,
    'theWriter.write(String.format("M  V30 COUNTS %d %d"+NL,rcnt,pcnt));',
    'theWriter.write("M  V30 COUNTS "+rcnt+" "+pcnt+NL);',
  );
}

function changeMolecule(code) {
  code = replaceChecked(
    code,
    'stream.writeLong(mAtomQueryFeatures[atom]);',
    '',
  );
  code = replaceChecked(
    code,
    'mAtomQueryFeatures[atom] = stream.readLong();',
    '',
  );
  return code;
}

function changeBaseConformer(code) {
  code = replaceChecked(
    code,
    'import java.util.ArrayList;',
    'import java.util.Arrays;\nimport java.util.ArrayList;',
  );
  code = replaceChecked(
    code,
    'mTorsion[bondIndex] = rotatableBond[bondIndex].getDefaultTorsions().clone();',
    'short[] torsions = rotatableBond[bondIndex].getDefaultTorsions();\n			mTorsion[bondIndex] = Arrays.copyOf(torsions, torsions.length);',
  );
  code = replaceChecked(
    code,
    'mFrequency[bondIndex] = rotatableBond[bondIndex].getDefaultFrequencies().clone();',
    'short[] frequencies = rotatableBond[bondIndex].getDefaultFrequencies();\n			mFrequency[bondIndex] = Arrays.copyOf(frequencies, frequencies.length);',
  );
  return code;
}

function changeConformerGenerator(code) {
  code = replaceChecked(
    code,
    'fragmentPermutation.clone()',
    'Arrays.copyOf(fragmentPermutation, fragmentPermutation.length)',
  );
  return code;
}

function changeSelfOrganizedConformer(code) {
  code = replaceChecked(
    code,
    'import java.util.ArrayList;',
    'import java.util.Arrays;\nimport java.util.ArrayList;',
  );
  code = replaceChecked(
    code,
    'conformer.mAtomStrain.clone()',
    'Arrays.copyOf(conformer.mAtomStrain, conformer.mAtomStrain.length)',
  );
  code = replaceChecked(
    code,
    'conformer.mRuleStrain.clone()',
    'Arrays.copyOf(conformer.mRuleStrain, conformer.mRuleStrain.length)',
  );
  return code;
}

function changeRigidFragmentCache(code) {
  code = replaceChecked(
    code,
    'import java.io.*;',
    'import java.io.*;\nimport org.cheminfo.utils.FakeFileInputStream;',
  );
  code = replaceChecked(
    code,
    'RigidFragmentCache.class.getResourceAsStream(',
    'FakeFileInputStream.getResourceAsStream(',
  );
  code = removeSlice(
    code,
    'public static RigidFragmentCache createCache',
    'return cache;\n	}',
  );
  code = removeSlice(code, 'public boolean serializeCache', 'return false;\n	}');
  code = removeSlice(
    code,
    'public boolean writeTabDelimitedTable',
    'return false;\n	}',
  );
  code = removeSlice(
    code,
    'public void loadCache(String cacheFileName)',
    '\n	}',
  );
  code = removeSlice(
    code,
    'public static RigidFragmentCache createInstance',
    'return cache;\n	}',
  );
  code = removeSlice(code, 'private static long addFragmentsToCacheSMP', '\n	}');
  code = removeSlice(
    code,
    'private static void consumeMoleculesToCacheFragments',
    '\n	}',
  );
  return code;
}

function changeCsv(code) {
  code = replaceChecked(
    code,
    'import java.io.InputStreamReader;',
    'import java.io.InputStreamReader;\nimport org.cheminfo.utils.FakeFileInputStream;',
  );
  code = replaceChecked(
    code,
    'Csv.class.getResourceAsStream(',
    'FakeFileInputStream.getResourceAsStream(',
  );
  return code;
}

function replaceStandardCharsets(times) {
  return (code) => {
    code = replaceChecked(code, 'StandardCharsets.UTF_8', '"UTF-8"', times);
    return code;
  };
}

function replaceHashTable(code) {
  return code.replaceAll('Hashtable', 'HashMap');
}

function changeVector3(code) {
  return removeSlice(code, 'public String toString() {', '}');
}

function changeInteractionDistanceStatistics(code) {
  code = replaceChecked(
    code,
    'InteractionDistanceStatistics.class.getResource(file);',
    'null;',
  );
  return code;
}

function fixCompoundFileHelper(code) {
  code = code.replaceAll(methodRegExp('saveRXNFile', { indent: '\t\t' }), '');
  code = code.replaceAll(
    methodRegExp('createFileFilter', { indent: '\t\t' }),
    '',
  );
  return code;
}

function changeBondOrderCalculator(code) {
  code = replaceChecked(
    code,
    'isAromaticBond.clone()',
    'Arrays.copyOf(isAromaticBond, isAromaticBond.length)',
  );
  return code;
}

function changeBondsCalculator(code) {
  code = replaceChecked(
    code,
    'isAromaticBond.clone()',
    'Arrays.copyOf(isAromaticBond, isAromaticBond.length)',
  );
  return code;
}
