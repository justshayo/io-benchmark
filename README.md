# Java vs. C++ I/O Benchmark

This project compares a few common integer-input strategies in Java and C++ using the same generated dataset.

## Project layout

```text
io-benchmark/
├── .gitignore
├── README.md
├── DataGen.java
├── JavaBenchmark.java
├── cpp_benchmark.cpp
└── input.txt   # generated locally, ignored by Git
```

## Benchmark goals

- Use the same 10,000,000 integer dataset for every benchmark
- Keep the generated input deterministic with a fixed random seed
- Check that the code does not get optimized away by summing every number into a checksum

## Benchmark summary

| Rank | Method | Language | Checksum output |
|---|---|---:|---|
| 1 | Scanner | Java | `long sum` |
| 2 | BufferedReader + StringTokenizer | Java | `long sum` |
| 3 | Custom 64KB byte parser | Java | `long sum` |
| 4 | Default `std::cin` | C++ | `long long sum` |
| 5 | `std::cin` with `ios::sync_with_stdio(false)` | C++ | `long long sum` |

## Generate the dataset

```powershell
cd "C:\Users\USER\OneDrive\Desktop\Benchmark suite\io-benchmark"
javac DataGen.java
java DataGen
```

This creates `input.txt` in the same folder as the source files.

## Run the Java benchmark

```powershell
cd "C:\Users\USER\OneDrive\Desktop\Benchmark suite\io-benchmark"
javac JavaBenchmark.java
java JavaBenchmark
```

## Run the C++ benchmark

```powershell
cd "C:\Users\USER\OneDrive\Desktop\Benchmark suite\io-benchmark"
cl.exe /EHsc /nologo cpp_benchmark.cpp /Fe:cpp_benchmark.exe
.\cpp_benchmark.exe
```

## Notes

- The dataset is intentionally generated with `Random(42)`.
- All benchmarks read `input.txt` from the current working directory so the relative path stays identical everywhere.
- Large generated files and compiled binaries are blocked in `.gitignore`, including object files such as `*.obj`.
