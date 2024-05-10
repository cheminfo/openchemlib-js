'use strict';

const modified = [
  'calc/ArrayUtilsCalc',

  'chem/io/DWARFileParser',
  'chem/io/Mol2FileParser',
  'chem/io/ODEFileParser',

  'chem/prediction/DruglikenessPredictor',
  'chem/prediction/IncrementTable',
  'chem/prediction/ToxicityPredictor',

  'gui/hidpi/HiDPIHelper',

  'util/ConstantsDWAR',
];

exports.modified = modified.map(getFilename);

const changed = [
  [
    '@org/openmolecules/chem/conf/gen/ConformerSetDiagnostics',
    changeConformerSetDiagnostics,
  ],
  ['@org/openmolecules/chem/conf/gen/BaseConformer', changeBaseConformer],
  [
    '@org/openmolecules/chem/conf/gen/ConformerGenerator',
    changeConformerGenerator,
  ],
  [
    '@org/openmolecules/chem/conf/so/ConformationSelfOrganizer',
    changeConformationSelfOrganizer,
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
  ['chem/TautomerHelper', changeTautomerHelper],
  ['chem/TextDrawingObject', changeTextDrawingObject],
  ['gui/editor/GenericEditorArea', changeGenericEditorArea],
  ['gui/editor/CustomAtomDialogBuilder', changeCustomAtomDialogBuilder],
  ['share/gui/ChemistryGeometryHelper', removePrintf],
  ['share/gui/editor/Model', removePrintf],
  ['util/ArrayUtils', changeArrayUtils],
  ['util/datamodel/IntVec', changeIntVec],
];

exports.changed = changed.map(([path, ...transformers]) => {
  return [getFilename(path), transformers];
});

const removed = [
  '@org/machinelearning',
  'calc/BoxCox.java',
  'calc/classification',
  'calc/combinatorics',
  'calc/distance',
  'calc/filter',
  'calc/graph',
  'calc/histogram',
  'calc/BinarySOM.java',
  'calc/LUDecomposition.java',
  'calc/Matrix.java',
  'calc/MatrixFunctions.java',
  'calc/MatrixTests.java',
  'calc/regression',
  'calc/ScaleClasses.java',
  'calc/SelfOrganizedMap.java',
  'calc/SimilarityMulticore.java',
  'calc/SOMController.java',
  'calc/statistics',
  'calc/VectorSOM.java',
  'chem/alignment3d',
  'chem/AtomTypeList.java',
  'chem/chemicalspaces',
  'chem/Clusterer.java',
  'chem/conf/BondRotationHelper.java',
  'chem/conf/ConformerSetGenerator.java',
  'chem/conf/MolecularFlexibilityCalculator.java',
  'chem/conf/SymmetryCorrectedRMSDCalculator.java',
  'chem/conf/torsionstrain',
  'chem/contrib/DiastereoIDTest.java',
  'chem/descriptor/DescriptorHandlerBinarySkelSpheres.java',
  'chem/descriptor/DescriptorHandlerFlexophore.java',
  'chem/descriptor/DescriptorHandlerFunctionalGroups.java',
  'chem/descriptor/DescriptorHandlerHashedCFp.java',
  'chem/descriptor/DescriptorHandlerLongPFP512.java',
  'chem/descriptor/DescriptorHandlerPFP512.java',
  'chem/descriptor/DescriptorHandlerStandardFactory.java',
  'chem/descriptor/DescriptorHandlerStandard2DFactory.java',
  'chem/descriptor/FingerPrintGenerator.java',
  'chem/descriptor/flexophore',
  'chem/descriptor/pharmacophoregraph',
  'chem/descriptor/pharmacophoretree',
  'chem/dnd', // ui
  'chem/docking',
  'chem/forcefield/mmff/Sdf.java', // needs access to disk
  'chem/hyperspace',
  'chem/interactionstatistics',
  'chem/io/AbstractParser.java',
  'chem/io/CompoundFileFilter.java',
  'chem/io/DWARFileCreator.java',
  'chem/io/NativeMDLReactionReader.java',
  'chem/io/pdb',
  'chem/mmp',
  'chem/Molecule3DFunctions.java',
  'chem/optimization/MCHelper.java',
  'chem/phesa',
  'chem/phesaflex',
  'chem/potentialenergy',
  'chem/prediction/FastMolecularComplexityCalculator.java',
  'chem/prediction/IncrementTableWithIndex.java',
  'chem/prediction/MolecularPropertyHelper.java',
  'chem/properties/complexity',
  'chem/properties/fractaldimension',
  'chem/reaction/ClassificationData.java',
  'chem/reaction/FunctionalGroupClassifier.java',
  'chem/reaction/mapping',
  'chem/reaction/ReactionClassifier.java',
  'chem/reaction/ReactionSearch.java',
  'chem/RingHelper.java',
  'chem/shredder/Fragment.java',
  'chem/StructureSearch.java',
  'jfx',
  'gui/clipboard/ClipboardHandler.java',
  'gui/clipboard/external',
  'gui/clipboard/ImageClipboardHandler.java',
  'gui/clipboard/NativeClipboardAccessor.java',
  'gui/clipboard/TextClipboardHandler.java',
  'gui/CompoundCollectionPane.java',
  'gui/dnd',
  'gui/dock',
  'gui/editor/FXEditorArea.java',
  'gui/editor/FXEditorDialog.java',
  'gui/editor/FXEditorPane.java',
  'gui/editor/FXEditorToolbar.java',
  'gui/editor/SwingEditorArea.java',
  'gui/editor/SwingEditorDialog.java',
  'gui/editor/SwingEditorPanel.java',
  'gui/editor/SwingEditorToolbar.java',
  'gui/fx',
  'gui/HeaderPaintHelper.java',
  'gui/hidpi',
  'gui/JAtomLabelDialog.java',
  'gui/JAtomQueryFeatureDialog.java',
  'gui/JBondQueryFeatureDialog.java',
  'gui/JChemistryView.java',
  'gui/JDrawArea.java',
  'gui/JDrawDialog.java',
  'gui/JDrawPanel.java',
  'gui/JDrawToolbar.java',
  'gui/JEditableChemistryView.java',
  'gui/JEditableStructureView.java',
  'gui/JImagePanel.java',
  'gui/JImagePanelFixedSize.java',
  'gui/JMessageBar.java',
  'gui/JMultiPanelTitle.java',
  'gui/JMultiPanelView.java',
  'gui/JPopupButton.java',
  'gui/JProgressDialog.java',
  'gui/JProgressPanel.java',
  'gui/JPruningBar.java',
  'gui/JScrollableMenu.java',
  'gui/JStructureView.java',
  'gui/JTextDrawingObjectDialog.java',
  'gui/MultiPanelDragListener.java',
  'gui/PopupItemProvider.java',
  'gui/ScrollPaneAutoScrollerWhenDragging.java',
  'gui/table',
  'gui/VerticalFlowLayout.java',
  'gui/wmf',
  'io/StringReadChannel.java',
  'share/gui/editor/chem/DrawingObject.java',
  'util/Base64.java',
  'util/BinaryEncoder.java',
  'util/BrowserControl.java',
  'util/CommandLineParser.java',
  'util/concurrent',
  'util/convert/String2DoubleArray.java',
  'util/Formatter.java',
  'util/graph',
  'util/IO.java',
  'util/IntQueue.java', // unused, depends on ArrayUtils
  'util/LittleEndianDataInputStream.java',
  'util/LittleEndianDataOutputStream.java',
  'util/MatrixSparse.java',
  'util/Pipeline.java',
  'util/Pipeline2FileWriter.java',
  'util/Platform.java',
  'util/Prefs.java',
  'util/SizeOf.java',
  'util/Sketch.java',
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

function removePrintf(code) {
  return code.replace(/System\.out\.printf/g, '// System.out.print');
}

function removeToStringSpaceDelimited(code) {
  return code.replaceAll(methodRegExp('toStringSpaceDelimited'), '');
}

function replaceChecked(code, from, to, times = 1) {
  for (let i = 0; i < times; i++) {
    if (code.indexOf(from) === -1) {
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
  return code.substr(0, startIdx) + code.substr(endIdx + end.length);
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
  return replaceChecked(
    code,
    'detail.append(String.format(" size=\\"%.4f\\"", new Double(mSize)));',
    'detail.append(" size=\\""+mSize+"\\"");',
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
  return code;
}

function changeCustomAtomDialogBuilder(code) {
  code = replaceChecked(code, 'e.getSource() instanceof JTextField', 'false');
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
    'theWriter.write("M  V30 COUNTS "+rcnt+" "+pcnt+NL,rcnt,pcnt);',
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
  return code.replaceAll('System.lineSeparator()', '"\\n"');
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
    code.substr(0, initIndexStart) +
    newInitialize +
    code.substr(initIndexEnd, code.length);

  return code;
}

function changeConformerSetDiagnostics(code) {
  code = code.replaceAll(
    /BufferedWriter writer = new BufferedWriter.*/g,
    'BufferedWriter writer = new BufferedWriter();',
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
    'java.io.InputStreamReader;',
    'java.io.StringReader;',
  );
  code = replaceChecked(
    code,
    'br = new BufferedReader(new InputStreamReader(Csv.class.getResourceAsStream(path), StandardCharsets.UTF_8));',
    'br = new BufferedReader(new StringReader(path));',
  );
  const fnfeStart = code.indexOf('catch (FileNotFoundException e) {');
  const fnfeEnd = code.indexOf('}', fnfeStart);
  code = code.substr(0, fnfeStart) + code.substr(fnfeEnd + 1, code.length);
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

function fixCompoundFileHelper(code) {
  code = code.replaceAll(methodRegExp('saveRXNFile', { indent: '\t\t' }), '');
  code = code.replaceAll('File.separatorChar', '10');
  code = code.replaceAll(
    methodRegExp('createFileFilter', { indent: '\t\t' }),
    '',
  );
  code = code.replaceAll('file.getName()', '""');
  return code;
}

function changeConformationSelfOrganizer(code) {
  code = code.replace('import java.io.FileOutputStream;\n', '');
  code = code.replace(
    'import java.io.OutputStreamWriter;\nimport java.nio.charset.StandardCharsets;\n',
    '',
  );
  code = code.replace(
    /mDWWriter = new BufferedWriter.*/,
    'mDWWriter = new BufferedWriter();',
  );
  return code;
}
