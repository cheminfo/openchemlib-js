const changeCountDiv = document.getElementById('changeCount') as HTMLElement;

export function incrementChangeCount() {
  const currentChangeCount = parseInt(changeCountDiv.innerText, 10);
  changeCountDiv.innerText = String(currentChangeCount + 1);
}

export function resetChangeCount() {
  changeCountDiv.innerText = '0';
}

const idcodeDiv = document.getElementById('idcode') as HTMLElement;

export function updateIDCode(idCode: string) {
  idcodeDiv.innerText = idCode.replaceAll('\x7f', '?');
}

const molfileArea = document.getElementById('molfile') as HTMLTextAreaElement;

export function updateMolfileOrRxn(value: string) {
  molfileArea.value = value;
}

const molfileV3Area = document.getElementById(
  'molfilev3',
) as HTMLTextAreaElement;

export function updateMolfileOrRxnV3(value: string) {
  molfileV3Area.value = value;
}
