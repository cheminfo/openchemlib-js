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
    ['chem/Molecule', changeMolecule]
];

exports.changed = changed.map((file) => {
    return [
        getFilename(file[0]),
        file[1]
    ];
});

function getFilename(file) {
    return 'actelion/research/' + file + '.java';
}

function changeMolecule(molecule) {
    molecule = molecule.replace('import java.lang.reflect.Array;\n', '');
    const copyOf = 'private final static Object copyOf';
    const copyOfIndex = molecule.indexOf(copyOf);
    const closeIndex = molecule.indexOf('}', copyOfIndex);
    molecule = molecule.substr(0, closeIndex + 1) + '*/' + molecule.substr(closeIndex + 1);
    molecule = molecule.substr(0, copyOfIndex) + '/*' + molecule.substr(copyOfIndex);
    molecule = molecule.replace(/\([^)]+\)copyOf/g, 'Arrays.copyOf');
    return molecule;
}
