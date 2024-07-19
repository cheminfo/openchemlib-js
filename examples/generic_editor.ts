import OCL from '../distesm/full.pretty';

const editorArea = document.getElementById('editor-area') as HTMLElement;

const editor = new OCL.CanvasEditor(editorArea, {
  width: 500,
  height: 500,
});

const molecule = OCL.Molecule.fromSmiles('COCO');

editor.setMolecule(molecule);
