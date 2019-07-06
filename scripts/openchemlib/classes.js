'use strict';

/* eslint-disable no-tabs */

const modified = [
  'calc/ArrayUtilsCalc',

  'chem/AbstractDrawingObject',
  'chem/DepictorTransformation',

  'chem/io/DWARFileParser',

  'chem/prediction/DruglikenessPredictor',
  'chem/prediction/IncrementTable',
  'chem/prediction/ToxicityPredictor',

  'util/ConstantsDWAR'
];

exports.modified = modified.map(getFilename);

const changed = [
  ['chem/ChemistryHelper', removePrintf],
  ['chem/conf/BondLengthSet', changeBondLengthSet],
  ['chem/conf/TorsionDB', changeTorsionDB],
  ['chem/io/RXNFileV3Creator', removeRXNStringFormat],
  ['chem/Molecule', changeMolecule],
  ['share/gui/editor/Model', removePrintf],
  ['util/ArrayUtils', changeArrayUtils]
];

exports.changed = changed.map((file) => {
  return [getFilename(file[0]), file[1]];
});

const removed = [
  'calc/statistics/median/ModelMedianDouble.java', // uses StringFunctions
  'chem/dnd', // ui
  'chem/FingerPrintGenerator.java',
  'chem/reaction/ClassificationData.java',
  'gui/dnd', // ui
  'gui/hidpi', // ui
  'share/gui/editor/chem/DrawingObject.java',
  // 'util/ArrayUtils.java', // uses reflection
  'util/CursorHelper.java',
  'util/datamodel/IntVec.java',
  'util/IntQueue.java', // unused, depends on ArrayUtils
  'util/Platform.java',
  'util/StringFunctions.java' // uses RegExp things
];

exports.removed = removed.map(getFolderName);

const generated = [['chem/conf/TorsionDBData', require('./generateTorsionDBData')]];

exports.generated = generated.map((file) => [getFilename(file[0]), file[1]]);

function getFilename(file) {
  return `actelion/research/${file}.java`;
}

function getFolderName(file) {
  return `actelion/research/${file}`;
}

function changeMolecule(molecule) {
  molecule = molecule.replace('import java.lang.reflect.Array;\n', '');
  const copyOf = 'protected final static Object copyOf';
  const copyOfIndex = molecule.indexOf(copyOf);
  if (copyOfIndex === -1) throw new Error('did not find copyOf method');
  const closeIndex = molecule.indexOf('}', copyOfIndex);
  molecule = `${molecule.substr(0, closeIndex + 1)}*/${molecule.substr(
    closeIndex + 1
  )}`;
  molecule = `${molecule.substr(0, copyOfIndex)}/*${molecule.substr(
    copyOfIndex
  )}`;
  molecule = molecule.replace(/\([^)]+\)copyOf/g, 'Arrays.copyOf');
  return molecule;
}

function removePrintf(code) {
  return code.replace(/System\.out\.printf/g, '// System.out.print');
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
    'theWriter.write("M  V30 COUNTS "+rcnt+" "+pcnt+"\\n",rcnt,pcnt);'
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
  code = code.replace('util.TreeMap;', 'util.TreeMap;\nimport com.actelion.research.chem.conf.TorsionDBData;');

  const initIndexStart = code.indexOf('private void init');
  const initIndexEnd = code.indexOf('/**', initIndexStart);

  code = code.substr(0, initIndexStart) + newInit + code.substr(initIndexEnd, code.length);

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
  code = code.replace('chem.StereoMolecule;', 'chem.StereoMolecule;\nimport com.actelion.research.chem.conf.TorsionDBData;');

  const initIndexStart = code.indexOf('private static void initialize');
  const initIndexEnd = code.indexOf('\n\tpublic float getLength', initIndexStart);

  code = code.substr(0, initIndexStart) + newInitialize + code.substr(initIndexEnd, code.length);

  return code;
}
