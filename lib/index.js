import initCanvasEditor from './canvas_editor/init/index.js';
import { extendOCL } from './extend/index.js';
import OCL from './java/openchemlib.js';
import { addRegisterResources } from './register_resources.js';

initCanvasEditor(OCL);
extendOCL(OCL);
addRegisterResources(OCL);

// eslint-disable-next-line unicorn/prefer-export-from
export default OCL;

export const CanvasEditor = OCL.CanvasEditor;
export const registerCustomElement = OCL.registerCustomElement;

export const CanonizerUtil = OCL.CanonizerUtil;
export const ConformerGenerator = OCL.ConformerGenerator;
export const DrugScoreCalculator = OCL.DrugScoreCalculator;
export const DruglikenessPredictor = OCL.DruglikenessPredictor;
export const ForceFieldMMFF94 = OCL.ForceFieldMMFF94;
export const Molecule = OCL.Molecule;
export const MoleculeProperties = OCL.MoleculeProperties;
export const Reaction = OCL.Reaction;
export const ReactionEncoder = OCL.ReactionEncoder;
export const Reactor = OCL.Reactor;
export const Resources = OCL.Resources;
export const RingCollection = OCL.RingCollection;
export const SDFileParser = OCL.SDFileParser;
export const SSSearcher = OCL.SSSearcher;
export const SSSearcherWithIndex = OCL.SSSearcherWithIndex;
export const SmilesParser = OCL.SmilesParser;
export const ToxicityPredictor = OCL.ToxicityPredictor;
export const Transformer = OCL.Transformer;
export const Util = OCL.Util;
export const version = OCL.version;
