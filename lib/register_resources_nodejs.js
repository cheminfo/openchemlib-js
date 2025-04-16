const allResources = [
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
];

export function addRegisterResourcesNodejs(OCL) {
  if (typeof process !== 'undefined') {
    OCL.Resources.registerResourcesNodejs = function registerResourcesNodejs() {
      const fs = process.getBuiltinModule('fs');
      const path = process.getBuiltinModule('path');
      for (const resource of allResources) {
        OCL.Resources.registerResource(
          resource,
          fs.readFileSync(path.join(import.meta.dirname, `../dist${resource}`)),
        );
      }
    };
  } else {
    OCL.Resources.registerResourcesNodejs = function registerResourcesNodejs() {
      throw new Error(`registerResourcesNodejs can only be called in Node.js`);
    };
  }
}
