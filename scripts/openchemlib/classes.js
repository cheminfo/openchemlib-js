'use strict';

/* eslint-disable no-tabs,import/order */

const modified = [
  'calc/ArrayUtilsCalc',

  'chem/AbstractDrawingObject',
  'chem/DepictorTransformation',

  'chem/io/DWARFileParser',

  'chem/prediction/DruglikenessPredictor',
  'chem/prediction/IncrementTable',
  'chem/prediction/ToxicityPredictor',

  'util/ConstantsDWAR',
];

exports.modified = modified.map(getFilename);

const changed = [
  ['chem/ChemistryHelper', removePrintf],
  ['chem/Molecule3D', removeCloneInfos],
  ['chem/conf/BondLengthSet', changeBondLengthSet],
  ['@org/openmolecules/chem/conf/gen/RigidFragmentCache', removeCacheIO],
  ['chem/conf/TorsionDB', changeTorsionDB],
  ['chem/forcefield/mmff/Csv', changeCsv],
  ['chem/forcefield/mmff/Separation', replaceHashTable],
  ['chem/forcefield/mmff/Tables', changeTables],
  ['chem/forcefield/mmff/Vector3', changeVector3],
  ['chem/io/RXNFileV3Creator', removeRXNStringFormat],
  ['share/gui/editor/Model', removePrintf],
  ['util/ArrayUtils', changeArrayUtils],
];

exports.changed = changed.map((file) => {
  return [getFilename(file[0]), file[1]];
});

const removed = [
  'calc/statistics',
  'chem/descriptor/flexophore',
  'chem/descriptor/DescriptorHandlerFlexophore.java',
  'chem/descriptor/DescriptorHandlerFunctionalGroups.java',
  'chem/descriptor/DescriptorHandlerStandardFactory.java',
  'chem/descriptor/DescriptorHandlerStandard2DFactory.java',
  'chem/dnd', // ui
  'chem/FingerPrintGenerator.java',
  'chem/forcefield/mmff/Sdf.java', // needs access to disk
  'chem/mcs/MatchList.java',
  'chem/mcs/MatchListContainer.java',
  'chem/properties/complexity/ExhaustiveFragmentsStatistics.java',
  'chem/properties/complexity/MolecularComplexityCalculator.java',
  'chem/properties/fractaldimension',
  'chem/reaction/ClassificationData.java',
  'chem/reaction/FunctionalGroupClassifier.java',
  'chem/StructureSearch.java',
  'jfx',
  'gui/dnd',
  'gui/hidpi',
  'gui/CompoundCollectionPane.java',
  'gui/JChemistryView.java',
  'gui/JEditableChemistryView.java',
  'gui/JEditableStructureView.java',
  'gui/JStructureView.java',
  'gui/ScrollPaneAutoScrollerWhenDragging.java',
  'share/gui/editor/chem/DrawingObject.java',
  'util/CursorHelper.java',
  'util/datamodel/IntVec.java',
  'util/IntQueue.java', // unused, depends on ArrayUtils
  'util/Platform.java',
  'util/StringFunctions.java', // uses RegExp things
];

exports.removed = removed.map(getFolderName);

const generated = [
  ['chem/conf/TorsionDBData', require('./generateTorsionDBData')],
  ['chem/forcefield/mmff/CsvData', require('./generateCsvData')],
];

exports.generated = generated.map((file) => [getFilename(file[0]), file[1]]);

function getFilename(file) {
  if (file.startsWith('@org/')) {
    return `../org/${file.replace('@org/', '')}.java`;
  } else {
    return `actelion/research/${file}.java`;
  }
}

function getFolderName(file) {
  if (file.startsWith('@org/')) {
    return `../org/${file.replace('@org/', '')}`;
  } else {
    return `actelion/research/${file}`;
  }
}

function removePrintf(code) {
  return code.replace(/System\.out\.printf/g, '// System.out.print');
}

function removeCloneInfos(code) {
  return code.replace(
    'infos[a] = m.infos[i].clone();',
    '// infos[a] = m.infos[i].clone();',
  );
}

function changeArrayUtils(code) {
  code = removeSlice(code, '\n	/**\n	 * Resize an array', 'return newArray;\n	}');
  code = removeSlice(code, '\n	/**\n	 * Copy an array ', 'return newArray;\n	}');
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
  return code.substr(0, startIdx) + code.substr(endIdx + end.length);
}

function removeRXNStringFormat(code) {
  return code.replace(
    'theWriter.write(String.format("M  V30 COUNTS %d %d\\n",rcnt,pcnt));',
    'theWriter.write("M  V30 COUNTS "+rcnt+" "+pcnt+"\\n",rcnt,pcnt);',
  );
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
  code = code.replace(
    'util.TreeMap;',
    'util.TreeMap;\nimport com.actelion.research.chem.conf.TorsionDBData;',
  );

  const initIndexStart = code.indexOf('private void init');
  const initIndexEnd = code.indexOf('/**', initIndexStart);

  code =
    code.substr(0, initIndexStart) +
    newInit +
    code.substr(initIndexEnd, code.length);

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
  code = code.replace(
    'chem.StereoMolecule;',
    'chem.StereoMolecule;\nimport com.actelion.research.chem.conf.TorsionDBData;',
  );

  const initIndexStart = code.indexOf('private static void initialize');
  const initIndexEnd = code.indexOf(
    '\n\tpublic float getLength',
    initIndexStart,
  );

  code =
    code.substr(0, initIndexStart) +
    newInitialize +
    code.substr(initIndexEnd, code.length);

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
  code = code.replace('java.io.InputStreamReader;', 'java.io.StringReader;');
  code = code.replace(
    'br = new BufferedReader(new InputStreamReader(Csv.class.getResourceAsStream(path)));',
    'br = new BufferedReader(new StringReader(path));',
  );
  const fnfeStart = code.indexOf('catch (FileNotFoundException e) {');
  const fnfeEnd = code.indexOf('}', fnfeStart);
  code = code.substr(0, fnfeStart) + code.substr(fnfeEnd + 1, code.length);
  return code;
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

  code =
    code.substr(0, indexStart) +
    newTables +
    code.substr(indexEnd + 1, code.length);

  return code;
}

function replaceHashTable(code) {
  return code.replace(/Hashtable/g, 'HashMap');
}

function changeVector3(code) {
  return removeSlice(code, 'public String toString() {', '}');
}
