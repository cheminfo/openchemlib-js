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
  ['chem/Coordinates', removeToStringSpaceDelimited],
  ['chem/coords/InventorFragment', changeInventorFragment],
  ['chem/conf/BondLengthSet', changeBondLengthSet],
  ['chem/conf/TorsionDB', changeTorsionDB],
  ['chem/forcefield/mmff/Csv', changeCsv],
  ['chem/forcefield/mmff/Separation', replaceHashTable],
  ['chem/forcefield/mmff/Tables', changeTables],
  ['chem/forcefield/mmff/Vector3', changeVector3],
  ['chem/io/CompoundFileHelper', fixCompoundFileHelper],
  ['chem/io/RXNFileCreator', changeLineSeparator],
  ['chem/io/RXNFileParser', replaceStandardCharsets(2)],
  ['chem/io/RXNFileV3Creator', changeLineSeparator, removeRXNStringFormat],
  ['chem/io/SDFileParser', replaceStandardCharsets(2)],
  ['chem/Molecule', changeMolecule],
  ['chem/MolfileCreator', changeLineSeparator],
  ['chem/MolfileParser', replaceStandardCharsets(1)],
  ['chem/MolfileV3Creator', changeLineSeparator],
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

function changeLineSeparator(code) {
  return code.replaceAll('System.lineSeparator()', String.raw`"\n"`);
}

const newInit = `
private void init(int mode) {
  mSupportedModes |= mode;

  String[] tr = TorsionDBData.gettorsionIDData();
  String[] ar = ((mode & MODE_ANGLES) == 0) ? null : TorsionDBData.gettorsionAngleData();
  String[] rr = ((mode & MODE_ANGLES) == 0) ? null : TorsionDBData.gettorsionRangeData();
  String[] fr = ((mode & MODE_ANGLES) == 0) ? null : TorsionDBData.gettorsionFrequencyData();
  String[] br = ((mode & MODE_BINS) == 0) ? null : TorsionDBData.gettorsionBinsData();

  for (int trLine = 0; trLine < tr.length; trLine++) {
    String type = tr[trLine];
    TorsionInfo torsionInfo = mTreeMap.get(type);
		if (torsionInfo == null) {
			torsionInfo = new TorsionInfo(getSymmetryType(type));
			mTreeMap.put(type, torsionInfo);
		}

		if (ar != null) {
			String[] angle = ar[trLine].split(",");
			torsionInfo.angle = new short[angle.length];
			for (int i=0; i<angle.length; i++)
				torsionInfo.angle[i] = Short.parseShort(angle[i]);
		}
		if (rr != null) {
			String[] range = rr[trLine].split(",");
			torsionInfo.range = new short[range.length][2];
			for (int i=0; i<range.length; i++) {
				int index = range[i].indexOf('-', 1);
				torsionInfo.range[i][0] = Short.parseShort(range[i].substring(0, index));
				torsionInfo.range[i][1] = Short.parseShort(range[i].substring(index+1));
			}
		}
		if (fr != null) {
			String[] frequency = fr[trLine].split(",");
			torsionInfo.frequency = new short[frequency.length];
			for (int i=0; i<frequency.length; i++)
				torsionInfo.frequency[i] = Byte.parseByte(frequency[i]);
		}
		if (br != null) {
			String[] binSize = br[trLine].split(",");
			torsionInfo.binSize = new byte[binSize.length];
			for (int i=0; i<binSize.length; i++)
				torsionInfo.binSize[i] = Byte.parseByte(binSize[i]);
		}
  }
}
`;

function changeTorsionDB(code) {
  code = replaceChecked(
    code,
    'util.TreeMap;',
    'util.TreeMap;\nimport com.actelion.research.chem.conf.TorsionDBData;',
  );

  const initIndexStart = code.indexOf('private void init');
  const initIndexEnd = code.indexOf('/**', initIndexStart);

  code = code.slice(0, initIndexStart) + newInit + code.slice(initIndexEnd);

  return code;
}

const newInitialize = `
private static void initialize() {
  if (!isInitialized) {
    String[] bdr = TorsionDBData.getbondLengthDataData();
    String countString = bdr[0];
    int count = Integer.parseInt(countString);

    BOND_ID = new int[count];
    BOND_LENGTH = new float[count];
    BOND_STDDEV = new float[count];
    BOND_COUNT = new int[count];

    for (int i=0; i<count; i++) {
      String line = bdr[i+1];
      String[] item = line.split("\\t");
      if (item.length == 4) {
        try {
          BOND_ID[i] = Integer.parseInt(item[0]);
          BOND_LENGTH[i] = Float.parseFloat(item[1]);
          BOND_STDDEV[i] = Float.parseFloat(item[2]);
          BOND_COUNT[i] = Integer.parseInt(item[3]);
        } catch (NumberFormatException nfe) {
          break;
        }
      }
    }
    isInitialized = true;
  }
}

`;

function changeBondLengthSet(code) {
  code = replaceChecked(
    code,
    'chem.StereoMolecule;',
    'chem.StereoMolecule;\nimport com.actelion.research.chem.conf.TorsionDBData;',
  );

  const initIndexStart = code.indexOf('private static void initialize');
  const initIndexEnd = code.indexOf(
    '\n\tpublic float getLength',
    initIndexStart,
  );

  code =
    code.slice(0, initIndexStart) + newInitialize + code.slice(initIndexEnd);

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

const newTables = `public static Tables newMMFF94(String tableSet) {
  return new com.actelion.research.chem.forcefield.mmff.Tables(
    CsvData.angleData,
    CsvData.atomData,
    CsvData.bciData,
    CsvData.bndkData,
    CsvData.bondData,
    CsvData.covradData,
    CsvData.dfsbData,
    CsvData.defData,
    CsvData.herschbachlaurieData,
    (tableSet.equals(ForceFieldMMFF94.MMFF94S) || tableSet.equals(ForceFieldMMFF94.MMFF94SPLUS) ? CsvData.n94s_outofplaneData : CsvData.outofplaneData),
    CsvData.pbciData,
    CsvData.stbnData,
    (tableSet.equals(ForceFieldMMFF94.MMFF94S) ? CsvData.n94s_torsionData : tableSet.equals(ForceFieldMMFF94.MMFF94SPLUS) ? CsvData.n94s_torsionPlusData : CsvData.torsionData),
    CsvData.vanderwaalsData
  );
}`;

function changeTables(code) {
  const indexStart = code.indexOf('public static Tables');
  const indexEnd = code.indexOf('}', indexStart);

  code = code.slice(0, indexStart) + newTables + code.slice(indexEnd + 1);

  return code;
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
