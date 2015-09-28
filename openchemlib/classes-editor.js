var classes = [
    // Requirements for Core
    'chem/AromaticityResolver',
    'chem/AtomFunctionAnalyzer',
    'chem/AtomTypeCalculator',
    'chem/Canonizer',
    'chem/CanonizerBaseValue',
    'chem/CanonizerMesoHelper',
    'chem/CoordinateInventor',
    'chem/ExtendedMolecule',
    'chem/IDCodeParser',
    'chem/IsotopeHelper',
    'chem/MolecularFormula',
    'chem/Molecule',
    'chem/MolfileCreator',
    'chem/MolfileParser',
    'chem/PropertyCalculator',
    'chem/RingCollection',
    'chem/SmilesCreator',
    'chem/SmilesParser',
    'chem/SortedStringList',
    'chem/SSSearcher',
    'chem/SSSearcherWithIndex',
    'chem/StereoMolecule',

    'chem/descriptor/DescriptorHandler',
    'chem/descriptor/DescriptorHandlerFactory',
    'chem/descriptor/DescriptorInfo',
    'chem/descriptor/ISimilarityCalculator',
    'chem/descriptor/ISimilarityHandlerFactory',
    'chem/descriptor/SimilarityCalculatorInfo',

    'chem/io/SDFileParser',
    'chem/io/CompoundFileParser',

    'chem/prediction/CLogPPredictor',
    'chem/prediction/PolarSurfaceAreaPredictor',
    'chem/prediction/ParameterizedStringList',
    'chem/prediction/SolubilityPredictor',

    'util/Angle',
    'util/SortedList',

    // Requirements for Viewer
    'chem/AbstractDepictor',
    'chem/DepictorTransformation',

    'share/gui/editor/geom/IDrawContext', // TODO not in ocl
    'share/gui/editor/geom/IPolygon', // TODO not in ocl

    'util/ColorHelper',

    // Requirements for Editor
    'calc/ArrayUtilsCalc',
    'calc/Matrix',

    'calc/statistics/median/ModelMedianDouble',

    'chem/ChemistryHelper',
    'chem/DrawingObjectList',
    'chem/ExtendedMoleculeFunctions',
    'chem/MarkushStructure',
    'chem/MolfileV3Creator',
    'chem/NamedSubstituents',

    'chem/mcs/MCS', // TODO not in ocl

    'chem/reaction/Reaction',
    'chem/reaction/ReactionMapper', // TODO not in ocl
    'chem/reaction/CommonSubGraphHelper', // TODO not in ocl

    'share/gui/Delegator',
    'share/gui/DialogResult',
    'share/gui/Polygon',

    'share/gui/editor/actions/Action', // TODO not in ocl
    'share/gui/editor/actions/AddRingAction', // TODO not in ocl
    'share/gui/editor/actions/AtomHighlightAction', // TODO not in ocl
    'share/gui/editor/actions/AtomMapAction', // TODO not in ocl
    'share/gui/editor/actions/BondBaseAction', // TODO not in ocl
    'share/gui/editor/actions/BondHighlightAction', // TODO not in ocl
    'share/gui/editor/actions/ChangeAtomAction', // TODO not in ocl
    'share/gui/editor/actions/ChangeAtomPropertiesAction', // TODO not in ocl
    'share/gui/editor/actions/ChangeChargeAction', // TODO not in ocl
    'share/gui/editor/actions/CleanAction', // TODO not in ocl
    'share/gui/editor/actions/ClearAction', // TODO not in ocl
    'share/gui/editor/actions/DownBondAction', // TODO not in ocl
    'share/gui/editor/actions/DrawAction', // TODO not in ocl
    'share/gui/editor/actions/DeleteAction', // TODO not in ocl
    'share/gui/editor/actions/NewBondAction', // TODO not in ocl
    'share/gui/editor/actions/NewChainAction', // TODO not in ocl
    'share/gui/editor/actions/SelectionAction', // TODO not in ocl
    'share/gui/editor/actions/UndoAction', // TODO not in ocl
    'share/gui/editor/actions/UnknownParityAction', // TODO not in ocl
    'share/gui/editor/actions/UpBondAction', // TODO not in ocl
    'share/gui/editor/actions/ZoomRotateAction', // TODO not in ocl

    'share/gui/editor/chem/IArrow', // TODO not in ocl
    'share/gui/editor/chem/IDepictor', // TODO not in ocl
    'share/gui/editor/chem/IDrawingObject', // TODO not in ocl

    'share/gui/editor/dialogs/IAtomPropertiesDialog', // TODO not in ocl
    'share/gui/editor/dialogs/IAtomQueryFeaturesDialog', // TODO not in ocl
    'share/gui/editor/dialogs/IBondQueryFeaturesDialog', // TODO not in ocl
    'share/gui/editor/dialogs/IDialog', // TODO not in ocl

    'share/gui/editor/geom/IColor', // TODO not in ocl
    'share/gui/editor/geom/ICursor', // TODO not in ocl

    'share/gui/editor/io/IKeyCode', // TODO not in ocl
    'share/gui/editor/io/IKeyEvent', // TODO not in ocl
    'share/gui/editor/io/IMouseEvent', // TODO not in ocl

    'share/gui/editor/listeners/IChangeListener', // TODO not in ocl
    'share/gui/editor/listeners/IValidationListener', // TODO not in ocl

    'util/BitUtils',
    'util/BurtleHasher',
    'util/BurtleHasherABC',

    'util/datamodel/IntArray',
    'util/datamodel/IntVec',
    'util/datamodel/PointDouble'
];

exports.copy = classes.map(getFilename);

var modified = [
    // Requirements for Viewer
    'chem/AbstractDrawingObject',
    'chem/DepictorTransformation',

    // Requirements for Editor
    'share/gui/editor/Model',

    'share/gui/editor/geom/GeomFactory'
];

exports.modified = modified.map(getFilename);

function getFilename(file) {
    return 'com/actelion/research/' + file + '.java';
}
