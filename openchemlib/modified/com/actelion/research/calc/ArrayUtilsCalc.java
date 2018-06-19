/*
* Copyright (c) 1997 - 2016
* Actelion Pharmaceuticals Ltd.
* Gewerbestrasse 16
* CH-4123 Allschwil, Switzerland
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice, this
*    list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
* 3. Neither the name of the the copyright holder nor the
*    names of its contributors may be used to endorse or promote products
*    derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*/

package com.actelion.research.calc;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ArrayUtilsCalc {
	/**
	 * Removes arrays which contains identical integer. The integer comparison 
	 * is independend from the order of the integer in the array. 
	 * @param li list with int [] as elements.
	 */
	public final static void removeDoubletsIntOrderIndepend(List<int []> li) {
		
		for(int i = 0; i<li.size();i++ ){
			for (int j = li.size() - 1; j > i; j--) {
				int [] a1 = li.get(i);
				int [] a2 = li.get(j);
				boolean bEq = true;
				
				for (int k = 0; k < a1.length; k++) {
					boolean bFound = false;
					for (int l = 0; l < a2.length; l++) {
						if(a1[k]==a2[l]){
							bFound = true;
							break;
						}
					}
					if(!bFound){
						bEq = false;
						break;
					}
				}
				if(bEq)
					li.remove(j);
			}
		}
  }
  
	/**
	 * Converts a List of Integer to an int[]
	 * @param list
	 * @return an array of int
	 */
	public final static int[] toIntArray(Collection<Integer> list) {
		int[] res = new int[list.size()];
		int index = 0;
		Iterator<Integer> iter = list.iterator();
		while(iter.hasNext()) {
			Integer i = (Integer) iter.next();
			res[index++] = i.intValue();
		}
		return res;
	}
}
