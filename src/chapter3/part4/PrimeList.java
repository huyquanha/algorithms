package chapter3.part4;

public class PrimeList {
    /**
     * The greatest prime <= 2^k (with k being the index in the array of the prime)
     * For k = 0, 2^0 = 1. But there's no prime smaller than 1 so we put -1
     * For k = 1, 2^1 = 2 and it is also a prime => we put 2
     * For k = 2, 2^2 = 4 and the greatest prime <= 4 is 3 => we put 3
     * ...
     */
    public static final int[] PRIMES = {-1, 2, 3, 7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191,
            16381, 32749, 65521, 131071, 262139, 524287, 1048673, 2097143, 4194301, 8388593, 16777213, 33554393,
            67108859, 134217689, 268435399, 536870909, 1073741789, 2147483647};
}
