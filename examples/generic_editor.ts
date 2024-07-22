import OCL from '../distesm/full.pretty';

const editorArea = document.getElementById('editor-area') as HTMLElement;
const eventsArea = document.getElementById('events-area') as HTMLElement;

const editor = new OCL.CanvasEditor(editorArea);

const molecule = OCL.Molecule.fromSmiles('COCO');
editor.setMolecule(molecule);

editor.setOnChangeListener((event) => {
  console.log('change', event);
  eventsArea.innerText = `Type: ${event.type}; User event: ${event.isUserEvent}`;
});
