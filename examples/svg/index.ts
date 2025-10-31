import { DepictorOptions, Molecule } from '../../lib/index.js';
import { cocaine } from '../seeds/molfile.ts';
import { moleculeFragment } from '../seeds/id_code.ts';
import { smilesWithStereoProblem } from '../seeds/smiles.ts';

// All keys are required so that if we add a new option in the type, it has to be added to the demo.
const allOptions: Record<Exclude<keyof DepictorOptions, 'maxAVBL'>, string> = {
  inflateToMaxAVBL: 'Inflate to max average bond length',
  inflateToHighResAVBL: 'Inflate to max average bond length (high resolution)',
  chiralTextBelowMolecule: 'Put chiral text below the molecule',
  chiralTextAboveMolecule: 'Put chiral text above the molecule',
  chiralTextOnFrameTop: 'Put chiral text at the top of the frame',
  chiralTextOnFrameBottom: 'Put chiral text at the bottom of the frame',
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
  noCarbonLabelWithCustomLabel:
    'Do not draw carbon symbol with superscript custom labels',
};

type BooleanDepictorOptions = Exclude<keyof DepictorOptions, 'maxAVBL'>;

const optionsWithDefaultTrue: BooleanDepictorOptions[] = [
  'inflateToMaxAVBL',
  'chiralTextBelowMolecule',
];

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
  if (optionsWithDefaultTrue.includes(name as BooleanDepictorOptions)) {
    checkbox.checked = true;
  }
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
  const { moleculeText, width, height, maxAVBL, ...checkedBoxes } = allData;
  const molecule = Molecule.fromText(moleculeText as string);
  if (molecule) {
    const depictorOptions: DepictorOptions = Object.fromEntries(
      Object.entries(checkedBoxes).map(([key]) => [key, true]),
    );
    depictorOptions.maxAVBL = Number(maxAVBL);
    for (const option of optionsWithDefaultTrue) {
      if (!depictorOptions[option]) {
        // If the option is not checked, set it explicitly to false so it doesn't inherit the default value.
        depictorOptions[option] = false;
      }
    }
    try {
      result.innerHTML = molecule.toSVG(
        Number(width),
        Number(height),
        undefined,
        depictorOptions,
      );
    } catch (error) {
      const errorParagraph = document.createElement('p');
      errorParagraph.textContent = `Error: ${error}`;
      result.replaceChildren(errorParagraph);
    }
  } else {
    result.innerHTML = '<p>Invalid molecule text</p>';
  }
});

const textarea = document.getElementById('moleculeText') as HTMLTextAreaElement;

function loadMolfile() {
  textarea.value = cocaine;
  form.requestSubmit();
}

const loadMolfileButton = document.getElementById(
  'loadMolfile',
) as HTMLButtonElement;
loadMolfileButton.onclick = loadMolfile;

function loadFragment() {
  textarea.value = moleculeFragment;
  form.requestSubmit();
}

const loadFragmentButton = document.getElementById(
  'loadFragment',
) as HTMLButtonElement;
loadFragmentButton.onclick = loadFragment;

function loadSmiles() {
  textarea.value = smilesWithStereoProblem;
  form.requestSubmit();
}

const loadSmilesButton = document.getElementById(
  'loadSmiles',
) as HTMLButtonElement;
loadSmilesButton.onclick = loadSmiles;

loadMolfile();
