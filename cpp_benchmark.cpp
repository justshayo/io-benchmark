#include <iostream>
#include <fstream>
#include <chrono>

using namespace std;
using namespace std::chrono;

void test_default_cin() {
    ifstream in("input.txt");
    streambuf* old = cin.rdbuf(in.rdbuf());

    auto start = high_resolution_clock::now();
    long long sum = 0;
    int x;
    while (cin >> x) {
        sum += x;
    }
    auto ms = duration_cast<milliseconds>(high_resolution_clock::now() - start).count();

    cin.rdbuf(old);
    cout << "4. C++ Default std::cin    : " << ms << " ms (checksum: " << sum << ")\n";
}

void test_fast_cin() {
    ifstream in("input.txt");
    streambuf* old = cin.rdbuf(in.rdbuf());
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    auto start = high_resolution_clock::now();
    long long sum = 0;
    int x;
    while (cin >> x) {
        sum += x;
    }
    auto ms = duration_cast<milliseconds>(high_resolution_clock::now() - start).count();

    cin.rdbuf(old);
    cout << "5. C++ Unsynced std::cin   : " << ms << " ms (checksum: " << sum << ")\n";
}

int main() {
    cout << "--- C++ I/O BENCHMARK (N = 10000000) ---\n";
    test_default_cin();
    cin.clear();
    test_fast_cin();
    return 0;
}
