'use strict';

const OCL = require('./dist/openchemlib-full.pretty.js');

OCL.CanvasEditor = require('./lib/canvas_editor')(
  OCL.EditorArea,
  OCL.EditorToolbar,
);

delete OCL.EditorArea;
delete OCL.EditorToolbar;

exports.default = OCL;
exports.CanonizerUtil = OCL.CanonizerUtil;
exports.CanvasEditor = OCL.CanvasEditor;
exports.ConformerGenerator = OCL.ConformerGenerator;
exports.DrugScoreCalculator = OCL.DrugScoreCalculator;
exports.DruglikenessPredictor = OCL.DruglikenessPredictor;
exports.ForceFieldMMFF94 = OCL.ForceFieldMMFF94;
exports.Molecule = OCL.Molecule;
exports.MoleculeProperties = OCL.MoleculeProperties;
exports.Reaction = OCL.Reaction;
exports.ReactionEncoder = OCL.ReactionEncoder;
exports.Reactor = OCL.Reactor;
exports.RingCollection = OCL.RingCollection;
exports.SDFileParser = OCL.SDFileParser;
exports.SSSearcher = OCL.SSSearcher;
exports.SSSearcherWithIndex = OCL.SSSearcherWithIndex;
exports.SVGRenderer = OCL.SVGRenderer;
exports.SmilesParser = OCL.SmilesParser;
exports.StructureEditor = OCL.StructureEditor;
exports.StructureView = OCL.StructureView;
exports.ToxicityPredictor = OCL.ToxicityPredictor;
exports.Transformer = OCL.Transformer;
exports.Util = OCL.Util;
exports.version = OCL.version;
