# photo-video-categorizer
**Effortlessly Organize Your Digital Memories**

The Photo and Video Categorizer is an intuitive and powerful tool designed to bring order to your sprawling collection of digital memories. Built with efficiency and ease of use in mind, this tool reads EXIF data from your images and videos, automatically organizing them into neatly sorted directories based on the date they were captured.

Key Features:
- EXIF Data Extraction: Harnesses the power of metadata extraction to read the embedded information in your photos and videos.
- Automated Categorization: Sorts your media files into folders by date, making it easy to navigate through your memories chronologically.
- Support for Various Formats: Compatible with a wide range of image and video formats, ensuring comprehensive coverage of your digital library.
- User-Friendly: Simple setup and execution, requiring minimal user input for maximum efficiency.
- Cross-Platform Compatibility: Works seamlessly on Windows, macOS, and Linux, catering to users across different operating systems.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

- Java JDK 8 or higher
- Maven 3.6.3 or higher

### Installing

A step-by-step series of examples that tell you how to get a development environment running:

* **Clone the Repository**
	
	git clone [your-repo-url]
	cd [your-project-name]
   
* **Build the Project**

Navigate to the ProjectDirectory and run:

	mvn clean package

This command compiles the project and packages it into a JAR file, along with copying the necessary dependencies into the lib directory.

### Usage

To run the application, use the provided batch script runCategorizer.bat. This script requires two parameters: the source directory path and the destination directory path.

##### Running the Application

Open a command prompt, navigate to your ProjectDirectory, and run:
	
	runCategorizer "path_to_source_directory" "path_to_destination_directory"

Replace path_to_source_directory and path_to_destination_directory with the paths where your source files are located and where you want the categorized files to be placed, respectively.

**Example**

	runCategorizer "G:\Photos_Videos\WorkDir" "G:\Photos_Videos\OutDir"

This will categorize the files in G:\Photos_Videos\WorkDir and place the organized files into G:\Photos_Videos\OutDir.

### Contributing

Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

### Versioning
We use SemVer for versioning. For the versions available, see the tags on this repository.

### Authors

- V. Oguz TOKMAK (vahapt) - Initial work
- ChatGPT4 - Boilerplate Coding

See also the list of contributors who participated in this project.

### License
This project is licensed under the MIT License - see the LICENSE.md file for details
