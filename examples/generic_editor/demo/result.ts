import OCL from '../../../distesm/full.pretty';

const changeCountDiv = document.getElementById('changeCount') as HTMLElement;

export function incrementChangeCount() {
  const currentChangeCount = parseInt(changeCountDiv.innerText, 10);
  changeCountDiv.innerText = String(currentChangeCount + 1);
}

export function resetChangeCount() {
  changeCountDiv.innerText = '0';
}

const idcodeDiv = document.getElementById('idcode') as HTMLElement;

export function updateIDCode(molecule: OCL.Molecule) {
  const idcodeAndCoords = molecule.getIDCodeAndCoordinates();
  idcodeDiv.innerText = `${idcodeAndCoords.idCode} ${idcodeAndCoords.coordinates}`;
}

const molfileDiv = document.getElementById('molfile') as HTMLElement;

export function updateMolfile(molecule: OCL.Molecule) {
  molfileDiv.innerText = molecule.toMolfileV3();
}
