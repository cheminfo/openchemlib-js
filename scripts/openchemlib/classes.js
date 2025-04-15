import { generateImageData } from './generateImageData.js';
import { removedClasses } from './removed.js';

const modifiedClasses = [
  'chem/io/DWARFileParser',
  'chem/io/Mol2FileParser',
  'chem/io/ODEFileParser',
  'gui/hidpi/HiDPIHelper',
  'gui/hidpi/HiDPIIcon',
  'util/ConstantsDWAR',
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
  ['@org/openmolecules/chem/conf/gen/RigidFragmentCache', removeCacheIO],
  ['chem/conf/TorsionDB', changeTorsionDB],
  ['chem/Coordinates', removeToStringSpaceDelimited],
  ['chem/coords/InventorFragment', changeInventorFragment],
  ['chem/forcefield/mmff/Csv', changeCsv],
  ['chem/forcefield/mmff/Separation', replaceHashTable],
  ['chem/forcefield/mmff/Vector3', changeVector3],
  ['chem/io/CompoundFileHelper', fixCompoundFileHelper],
  ['chem/io/RXNFileParser', replaceStandardCharsets(2)],
  ['chem/io/RXNFileV3Creator', removeRXNStringFormat],
  ['chem/io/SDFileParser', replaceStandardCharsets(2)],
  ['chem/Molecule', changeMolecule],
  ['chem/MolfileParser', replaceStandardCharsets(1)],
  ['chem/Molecule3D', removeCloneInfos],
  ['chem/prediction/IncrementTable', changeIncrementTable],
  ['chem/prediction/ToxicityPredictor', changeToxicityPredictor],
  ['chem/reaction/mapping/RootAtomPairSource', changeRootAtomPairSource],
  ['chem/reaction/mapping/ReactionCenterMapper', changeReactionCenterMapper],
  ['chem/TautomerHelper', changeTautomerHelper],
  ['chem/TextDrawingObject', changeTextDrawingObject],
  ['gui/editor/GenericEditorArea', changeGenericEditorArea],
  ['gui/editor/CustomAtomDialogBuilder', changeCustomAtomDialogBuilder],
  ['gui/generic/GenericDialog', changeGenericDialog],
  ['util/ArrayUtils', changeArrayUtils],
  ['util/datamodel/IntVec', changeIntVec],
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
    'TorsionDB.class.getResourceAsStream(',
    'new FakeFileInputStream(',
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

function removeCloneInfos(code) {
  return replaceChecked(
    code,
    'infos[a] = m.infos[i].clone();',
    '// infos[a] = m.infos[i].clone();',
  );
}

function changeIncrementTable(code) {
  code = replaceChecked(
    code,
    'this.getClass().getResourceAsStream(filename)',
    'new FakeFileInputStream(filename)',
  );
  return code;
}

function changeToxicityPredictor(code) {
  code = replaceChecked(
    code,
    'import java.io.IOException;',
    'import java.io.FakeFileInputStream;\nimport java.io.IOException;',
  );
  code = replaceChecked(
    code,
    'this.getClass().getResourceAsStream(filename)',
    'new FakeFileInputStream(filename)',
    2,
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
    String.raw`detail.append(String.format(" size=\"%.4f\"", new Double(mSize)));`,
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
  code = removeSlice(code, '\n	/**\n	 * Resize an array', 'return newArray;\n	}');
  code = removeSlice(code, '\n	/**\n	 * Copy an array ', 'return newArray;\n	}');
  return code;
}

function changeIntVec(code) {
  const options = { indent: '    ' };
  code = code.replace(methodRegExp('convert', options), '');
  code = code.replace(methodRegExp('read', options), '');
  code = code.replace(methodRegExp('readBitStringDense', options), '');
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

function removeCacheIO(code) {
  code = removeSlice(
    code,
    'public synchronized void loadDefaultCache() {',
    '\n		}',
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
    'import java.io.InputStreamReader;\nimport java.io.FakeFileInputStream;',
  );
  code = replaceChecked(
    code,
    'Csv.class.getResourceAsStream(path)',
    'new FakeFileInputStream(path)',
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

function fixCompoundFileHelper(code) {
  code = code.replaceAll(methodRegExp('saveRXNFile', { indent: '\t\t' }), '');
  code = code.replaceAll(
    methodRegExp('createFileFilter', { indent: '\t\t' }),
    '',
  );
  return code;
}
