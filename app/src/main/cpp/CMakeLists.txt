cmake_minimum_required(VERSION 3.4.1)

# Define your library
add_library(
        native-lib  # Library name
        SHARED      # Shared library (.so)
        native-lib.cpp  # Your source file
)

# Find log library
find_library(
        log-lib
        log
)

# Link libraries
target_link_libraries(
        native-lib
        ${log-lib}
)