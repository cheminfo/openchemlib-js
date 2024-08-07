export const rxn = `$RXN V3000
r3.rxn
  ChemDraw07242416532D

M  V30 COUNTS 2 1
M  V30 BEGIN REACTANT
M  V30 BEGIN CTAB
M  V30 COUNTS 6 5 0 0 0
M  V30 BEGIN ATOM
M  V30 1 C -1.428942 -0.618750 0.000000 2
M  V30 2 C -0.714472 -0.206250 0.000000 3
M  V30 3 C -0.000000 -0.618750 0.000000 4
M  V30 4 C 0.714471 -0.206250 0.000000 1
M  V30 5 Cl 1.428942 -0.618750 0.000000 0
M  V30 6 O 0.714471 0.618750 0.000000 5
M  V30 END ATOM
M  V30 BEGIN BOND
M  V30 1 1 1 2
M  V30 2 1 2 3
M  V30 3 1 3 4
M  V30 4 1 4 5 RXCTR=4
M  V30 5 2 4 6
M  V30 END BOND
M  V30 END CTAB
M  V30 BEGIN CTAB
M  V30 COUNTS 5 4 0 0 0
M  V30 BEGIN ATOM
M  V30 1 C -1.428942 -0.206250 0.000000 6
M  V30 2 C -0.714472 0.206250 0.000000 7
M  V30 3 N -0.000000 -0.206250 0.000000 8
M  V30 4 C 0.714471 0.206250 0.000000 9
M  V30 5 C 1.428942 -0.206250 0.000000 10
M  V30 END ATOM
M  V30 BEGIN BOND
M  V30 1 1 1 2
M  V30 2 1 2 3
M  V30 3 1 3 4
M  V30 4 1 4 5
M  V30 END BOND
M  V30 END CTAB
M  V30 END REACTANT
M  V30 BEGIN PRODUCT
M  V30 BEGIN CTAB
M  V30 COUNTS 10 9 0 0 0
M  V30 BEGIN ATOM
M  V30 1 C -2.143413 0.000000 0.000000 2
M  V30 2 C -1.428942 0.412500 0.000000 3
M  V30 3 C -0.714471 0.000000 0.000000 4
M  V30 4 C 0.000000 0.412500 0.000000 1
M  V30 5 N 0.714471 0.000000 0.000000 8
M  V30 6 O 0.000000 1.237500 0.000000 5
M  V30 7 C 1.428941 0.412501 0.000000 7
M  V30 8 C 0.714471 -0.824999 0.000000 9
M  V30 9 C 2.143413 0.000001 0.000000 6
M  V30 10 C 0.000000 -1.237500 0.000000 10
M  V30 END ATOM
M  V30 BEGIN BOND
M  V30 1 1 1 2
M  V30 2 1 2 3
M  V30 3 1 3 4
M  V30 4 1 4 5 RXCTR=4
M  V30 5 2 4 6
M  V30 6 1 5 7
M  V30 7 1 5 8
M  V30 8 1 7 9
M  V30 9 1 8 10
M  V30 END BOND
M  V30 END CTAB
M  V30 END PRODUCT
M  END`;

export default rxn;
