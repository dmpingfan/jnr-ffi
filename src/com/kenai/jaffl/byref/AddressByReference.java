
package com.kenai.jaffl.byref;

import com.kenai.jaffl.Address;
import com.kenai.jaffl.Pointer;
import com.kenai.jaffl.Runtime;

/**
 * AddressByReference is used when the address of a primitive pointer value must be passed
 * as a parameter to a function.
 *
 * <p>For example, the following C code,
 * <p><blockquote><pre>
 * {@code
 * extern void get_a(void** ap);
 *
 * void* foo(void) {
 *     void* a;
 *     // pass a reference to 'a' so get_a() can fill it out
 *     get_a(&a);
 *
 *     return a;
 * }
 * }
 * </pre></blockquote>
 *
 * <p>Would be declared in java as
 * <p><blockquote><pre>
 * {@code
 * interface Lib {
 *     void get_a(@Out AddressByReference ap);
 * }
 * }
 * </pre></blockquote>
 * <p>and used like this
 *
 * <p><blockquote><pre>
 * AddressByReference ap = new AddressByReference();
 * lib.get_a(ap);
 * System.out.println("a from lib=" + a.getValue());
 * </pre></blockquote>
 */
public final class AddressByReference extends AbstractReference<Address> {

    /**
     * Creates a new reference to an integer value
     */
    public AddressByReference() {
        super(Address.valueOf(0));
    }

    /**
     * Creates a new reference to an address value
     * 
     * @param value the initial native value
     */
    public AddressByReference(Address value) {
        super(checkNull(value));
    }
    
    /**
     * Copies the address value to native memory
     * 
     * @param memory the native memory buffer
     */
    public void marshal(Pointer memory, long offset) {
        memory.putAddress(offset, value.nativeAddress());
    }

    /**
     * Copies the address value from native memory
     * 
     * @param memory the native memory buffer.
     */
    public void unmarshal(Pointer memory, long offset) {
        value = Address.valueOf(memory.getAddress(offset));
    }
    
    /**
     * Gets the native size of type of reference
     * 
     * @return The size of the native type this reference points to
     */
    public int nativeSize(Runtime runtime) {
        return runtime.addressSize();
    }
}
