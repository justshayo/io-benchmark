# Java vs. C++ I/O Benchmark

A small benchmarking project that compares common integer-parsing strategies in Java and C++ using the same generated dataset.

The goal is to measure how quickly each approach reads and sums 10,000,000 integers while preventing dead-code elimination by printing a checksum after every run.

## Overview

This benchmark includes:

- **Java:** `Scanner`, `BufferedReader` + `StringTokenizer`, and a custom 64KB byte-buffer parser
- **C++:** default `std::cin` and `std::cin` with `ios_base::sync_with_stdio(false)`
- A shared generator for a fixed-size, deterministic input file: `input.txt`

## Project structure

```text
io-benchmark/
├── .gitignore
├── README.md
├── DataGen.java
├── JavaBenchmark.java
├── cpp_benchmark.cpp
└── input.txt   # generated locally, ignored by Git
```

## Benchmark design

- **Dataset size:** `10_000_000` integers (~78 MB)
- **Random seed:** `Random(42)`
- **Output check:** each benchmark computes a `long` or `long long` checksum and prints it to ensure the compiler/JIT cannot remove the loop work as dead code

## Benchmark comparison

Measured on Windows (JDK 25 / MSVC `/O2`) using the same generated dataset and checksum validation.

| Rank | Method | Language | Time (ms) | Speedup vs. Scanner | Checksum |
|---:|---|---|---:|---:|---:|
| 1 | Custom 64KB byte parser | Java | 104 | 26.86x | 5000868348918 |
| 2 | `BufferedReader` + `StringTokenizer` | Java | 519 | 5.38x | 5000868348918 |
| 3 | `std::cin` with `sync_with_stdio(false)` | C++ | 2,680 | 1.04x | 5000868348918 |
| 4 | `Scanner` | Java | 2,793 | 1.00x | 5000868348918 |
| 5 | Default `std::cin` | C++ | 2,949 | 0.95x | 5000868348918 |

## Generate the input dataset

From the project root:

```powershell
javac DataGen.java
java DataGen
```

This creates `input.txt` in the same directory as the source files.

## Run the Java benchmark

```powershell
javac JavaBenchmark.java
java JavaBenchmark
```

## Run the C++ benchmark

On Windows with MSVC:

```powershell
cl.exe /O2 /EHsc /std:c++17 cpp_benchmark.cpp /Fe:cpp_benchmark.exe
.\cpp_benchmark.exe
```

On Linux/macOS with GCC or Clang:

```bash
g++ -O3 -std=c++17 cpp_benchmark.cpp -o cpp_benchmark && ./cpp_benchmark
```

## Git hygiene

The project includes a [.gitignore](.gitignore) file that excludes:

- `input.txt`
- `*.class`
- `*.exe`
- `*.out`
- `*.o`
- `*.obj`
- compiled benchmark binaries
- editor folders such as `.idea/` and `.vscode/`

This keeps large generated datasets and local build artifacts out of GitHub.

## Notes

- The random generator uses a fixed seed so the benchmark is deterministic.
- All programs read `input.txt` from the same working directory to keep the data path consistent.
- This project is designed for local benchmarking and educational comparison, not as a production-grade parser library.

## License

This project is provided for educational and benchmarking purposes.
