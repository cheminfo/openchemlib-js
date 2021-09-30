import IDCodeParserWithoutCoordinateInvention from './IDCodeParserWithoutCoordinateInvention.js';

export default class IDCodeParser extends IDCodeParserWithoutCoordinateInvention {
  mEnsure2DCoordinates = false;

  constructor(ensure2DCoordinates) {
    super();
    this.mEnsure2DCoordinates = ensure2DCoordinates;
  }

  ensure2DCoordinates() {
    return this.mEnsure2DCoordinates;
  }

  inventCoordinates(mol) {
    const inventor = new CoordinateInventor();
    inventor.setRandomSeed(0x1234567890);
    inventor.invent(mol);
  }
}
