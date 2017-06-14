'use strict';

const modified = [
    'chem/AbstractDrawingObject',
    'chem/DepictorTransformation',

    'chem/prediction/DruglikenessPredictor',
    'chem/prediction/IncrementTable',
    'chem/prediction/ToxicityPredictor',
];

exports.modified = modified.map(getFilename);

const changed = [
    ['chem/Molecule', changeMolecule],
    ['share/gui/editor/Model', removePrintf]
];

exports.changed = changed.map((file) => {
    return [
        getFilename(file[0]),
        file[1]
    ];
});

const removed = [
    'chem/dnd', // ui
    'chem/FingerPrintGenerator.java',
    'chem/reaction/ClassificationData.java',
    'gui/dnd', // ui
    'gui/hidpi', // ui
    'share/gui/editor/chem/DrawingObject.java',
    'util/ArrayUtils.java', // uses reflection
    'util/CursorHelper.java',
    'util/datamodel/IntVec.java',
    'util/IntQueue.java', // unused, depends on ArrayUtils
    'util/Platform.java',
];

exports.removed = removed.map(getFolderName);

function getFilename(file) {
    return 'actelion/research/' + file + '.java';
}

function getFolderName(file) {
    return 'actelion/research/' + file;
}

function changeMolecule(molecule) {
    molecule = molecule.replace('import java.lang.reflect.Array;\n', '');
    const copyOf = 'protected final static Object copyOf';
    const copyOfIndex = molecule.indexOf(copyOf);
    if (copyOfIndex === -1) throw new Error('did not find copyOf method');
    const closeIndex = molecule.indexOf('}', copyOfIndex);
    molecule = molecule.substr(0, closeIndex + 1) + '*/' + molecule.substr(closeIndex + 1);
    molecule = molecule.substr(0, copyOfIndex) + '/*' + molecule.substr(copyOfIndex);
    molecule = molecule.replace(/\([^)]+\)copyOf/g, 'Arrays.copyOf');
    return molecule;
}

function removePrintf(code) {
    return code.replace('System.out.printf', 'System.out.print')
}
