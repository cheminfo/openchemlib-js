import { readFileSync } from 'node:fs';
import path from 'node:path';

import initCanvasEditor from './canvas_editor/init/index.js';
import { extendOCL } from './extend/index.js';
import OCL from './java/openchemlib.js';

initCanvasEditor(OCL);
extendOCL(OCL);

for (const resource of [
  '/resources/cod/torsionID.txt',
  '/resources/cod/torsionAngle.txt',
  '/resources/cod/torsionRange.txt',
  '/resources/cod/torsionFrequency.txt',
  '/resources/cod/bondLengthData.txt',
  '/resources/forcefield/mmff94/angle.csv',
  '/resources/forcefield/mmff94/pbci.csv',
  '/resources/forcefield/mmff94/bci.csv',
  '/resources/forcefield/mmff94/atom.csv',
  '/resources/forcefield/mmff94/bndk.csv',
  '/resources/forcefield/mmff94/bond.csv',
  '/resources/forcefield/mmff94/covrad.csv',
  '/resources/forcefield/mmff94/dfsb.csv',
  '/resources/forcefield/mmff94/def.csv',
  '/resources/forcefield/mmff94/herschbachlaurie.csv',
  '/resources/forcefield/mmff94/outofplane.csv',
  '/resources/forcefield/mmff94/stbn.csv',
  '/resources/forcefield/mmff94/torsion.csv',
  '/resources/forcefield/mmff94/vanderwaals.csv',
  '/resources/druglikenessNoIndex.txt',
  '/resources/toxpredictor/m1.txt',
  '/resources/toxpredictor/t1.txt',
  '/resources/toxpredictor/i1.txt',
  '/resources/toxpredictor/r1.txt',
  '/resources/toxpredictor/m2.txt',
  '/resources/toxpredictor/t2.txt',
  '/resources/toxpredictor/i2.txt',
  '/resources/toxpredictor/r2.txt',
  '/resources/toxpredictor/m3.txt',
  '/resources/toxpredictor/t3.txt',
  '/resources/toxpredictor/i3.txt',
  '/resources/toxpredictor/r3.txt',
]) {
  OCL.Resources.registerResource(
    resource,
    readFileSync(path.join(import.meta.dirname, `../dist${resource}`)),
  );
}

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
export const RingCollection = OCL.RingCollection;
export const SDFileParser = OCL.SDFileParser;
export const SSSearcher = OCL.SSSearcher;
export const SSSearcherWithIndex = OCL.SSSearcherWithIndex;
export const SmilesParser = OCL.SmilesParser;
export const ToxicityPredictor = OCL.ToxicityPredictor;
export const Transformer = OCL.Transformer;
export const Util = OCL.Util;
export const version = OCL.version;
