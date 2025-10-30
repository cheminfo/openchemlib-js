import { DepictorOptions, Molecule } from '../../lib/index.js';
import molfile from '../seeds/molfile.ts';

// All keys are required so that if we add a new option in the type, it has to be added to the demo.
const allOptions: Record<keyof DepictorOptions, string> = {
  inflateToMaxAVBL: 'Inflate to max average bond length',
  inflateToHighResAVBL: 'Multiply average bond length by 256 before encoding',
  chiralTextBelowMolecule: 'Put chiral text below the molecule',
  chiralTextAboveMolecule: 'Put chiral text above the molecule',
  chiralTextOnFrameTop: 'Put chiral text xxx',
  chiralTextOnFrameBottom: 'Put chiral text yyy',
  noTabus: 'No tabus',
  showAtomNumber: 'Show atom numbers',
  showBondNumber: 'Show bond numbers',
  highlightQueryFeatures: 'Highlight query features (fragment only)',
  showMapping: 'Show mapping',
  suppressChiralText: 'Suppress chiral text',
  suppressCIPParity: 'Suppress CIP parity',
  suppressESR: 'Suppress ESR',
  showSymmetryAny: 'Show all symmetries',
  showSymmetrySimple: 'Show simple symmetries',
  showSymmetryStereoHeterotopicity: 'Show stereo-heterotopicity symmetries',
  noImplicitAtomLabelColors: 'Disable implicit atom label colors',
  noStereoProblem: 'Disable stereo problems',
  noColorOnESRAndCIP: 'Disable coloring of ESR and CIP',
  noImplicitHydrogen: 'Do not draw implicit hydrogens',
  drawBondsInGray: 'Draw bonds in gray',
  noCarbonLabelWithCustomLabel: 'Do not draw carbon labels with custom labels',
};

const checkboxesContainer = document.getElementById(
  'checkboxes',
) as HTMLDivElement;

for (const [name, label] of Object.entries(allOptions)) {
  const div = document.createElement('div');
  const checkbox = document.createElement('input');
  checkbox.type = 'checkbox';
  checkbox.id = name;
  checkbox.name = name;
  checkbox.className = 'mr-1';
  const labelElement = document.createElement('label');
  labelElement.htmlFor = name;
  labelElement.textContent = label;
  div.appendChild(checkbox);
  div.appendChild(labelElement);
  checkboxesContainer.appendChild(div);
}

const form = document.getElementById('form') as HTMLFormElement;
const result = document.getElementById('result') as HTMLDivElement;
form.addEventListener('submit', (event) => {
  event.preventDefault();
  const formData = new FormData(form);
  const allData = Object.fromEntries(formData);
  const { moleculeText, width, height, ...checkedBoxes } = allData;
  const molecule = Molecule.fromText(moleculeText as string);
  if (molecule) {
    const depictorOptions = Object.fromEntries(
      Object.entries(checkedBoxes).map(([key]) => [key, true]),
    );
    console.log(depictorOptions);
    result.innerHTML = molecule.toSVG(
      Number(width),
      Number(height),
      undefined,
      depictorOptions,
    );
  } else {
    result.innerHTML = '<p>Invalid molecule text</p>';
  }
});

const textarea = document.getElementById('moleculeText') as HTMLTextAreaElement;
textarea.value = molfile;

form.requestSubmit();
