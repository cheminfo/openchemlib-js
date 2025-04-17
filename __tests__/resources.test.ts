import { describe, expect, it } from 'vitest';

import {
  ConformerGenerator,
  DruglikenessPredictor,
  ForceFieldMMFF94,
  Molecule,
  ToxicityPredictor,
} from '../lib/index.js';

describe('Resources', () => {
  it('should throw if resources have not been registered', () => {
    const errorMessage = 'static resources must be registered first';
    expect(() => new ConformerGenerator(0)).toThrow(errorMessage);
    expect(() => new DruglikenessPredictor()).toThrow(errorMessage);
    expect(() => new ForceFieldMMFF94(new Molecule(0, 0), 'MMFF94')).toThrow(
      errorMessage,
    );
    expect(() => new ToxicityPredictor()).toThrow(errorMessage);
  });
});
