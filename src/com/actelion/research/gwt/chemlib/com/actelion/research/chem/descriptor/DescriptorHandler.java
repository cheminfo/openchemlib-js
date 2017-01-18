/*

Copyright (c) 2015-2017, cheminfo

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.
    * Neither the name of {{ project }} nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.actelion.research.chem.descriptor;

/**
 * DescriptorHandler is the base Interface for any molecular descriptor
 * handling StereoMolecules or Reactions
 */

public interface DescriptorHandler<T, U> extends ISimilarityCalculator<T> {
    static final String FAILED_STRING = "Calculation Failed";
    static final byte[] FAILED_BYTES = FAILED_STRING.getBytes();
    public abstract DescriptorInfo getInfo();
    public abstract String getVersion();
	public abstract String encode(T o);
    public abstract T decode(String s);
    public abstract T decode(byte[] bytes);
    public abstract T createDescriptor(U chemObject);
    public abstract boolean calculationFailed(T o);
    public abstract DescriptorHandler<T,U> getThreadSafeCopy();
    }
