# Store Monitoring Project

Welcome to the Store Monitoring Project! This project aims to monitor the online status of various restaurants and provide reports on their uptime and downtime during business hours.

## Table of Contents
- [Overview](#overview)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Core Logic](#core-logic)
  
## Overview

The Store Monitoring Project allows restaurant owners to track the online status of their stores during business hours. The project provides APIs for generating reports that include uptime and downtime information.

## Technologies Used

The project is built using the following technologies:
- Java (version 11 or higher)
- Spring Boot (version 2.5.4 or higher)
- MySQL Database

## Getting Started

### Prerequisites

To run this application, you will need the following installed on your machine:
- Java Development Kit (JDK) 11 or higher
- MySQL Database
- Spring-boot v2.7.8 or Above

### Installation

1. Clone the Git repository to your local machine: git clone https://github.com/DhruvPrajapati4/Store-Monitoring-Tool.git

2. Setup the MySQL database using the DDL.sql file

3. Navigate to the project directory: cd src

4. Create/Modify the `application.properties` file in the `src/main/resources` directory and configure the MySQL database connection settings according to your local environment.

5. Build the application using Maven: mvn clean install

6. Run the application: mvn spring-boot:run

### Usage
1. Trigger a report generation by making a POST request to /api/trigger_report endpoint.
2. Poll the status of the report using the /api/get_report endpoint and the reportId obtained from step 1. Once the report is complete, you'll receive a CSV file containing the report data.

### API Endpoints
1. POST: /store_monitoring/trigger_report: Trigger report generation.
2. GET: /store_monitoring/get_report?reportId: Get the status of a report or the CSV file containing the report data.

### Core Logic

The Store Monitoring Project is designed to efficiently monitor the online status of multiple restaurants and generate comprehensive reports on their uptime and downtime during their business hours. The core logic of this project involves several key steps to ensure accurate data collection, processing, and reporting:

1. **Report Generation**:
   - Upon triggering report generation, the project fetches data from various sources:
     - `store_status` table for online status records.
     - `business_hours` table for store operating hours.
     - `store_timezone` table for store timezones.
   - For each status record:
     - Convert the UTC timestamp to the store's local time based on the respective timezone.
     - Determine if the timestamp falls within the store's business hours.
     - Update the uptime and downtime counters in the `StoreReport` object.

3. **Storing Reports**:
   - The generated reports are stored as a list of `Report` objects, each representing a specific restaurant's report.
   - After processing all status records, the individual store reports are aggregated into a single CSV data string.
   - A new `Report` object is created and populated with the following:
     - A unique Report ID generated based on the current date and time.
     - The CSV data containing aggregated store reports.
     - An initial status of "Running" to indicate report processing.

4. **Downloading Reports**:
   - Users can request report status or download a complete report using the `/api/get_report` endpoint by providing the `reportId`.
   - If the report status is "Running", the response indicates that the report is still being processed.
   - If the report status is "Complete", the CSV file containing the report data is generated and provided for download.

5. **Timezone Conversion**:
   - The project ensures accurate timestamp comparisons by converting UTC timestamps to the local time of each store based on its timezone.
   - The `convertToStoreLocalTime()` method performs this conversion.

6. **Business Hours and Uptime/Downtime Calculation**:
   - The project calculates the downtime and uptime for each store during their business hours.
   - If a store's status is "active", the uptime counters are incremented. If the status is "inactive", the downtime counters are incremented.

7. **CSV File Creation and Download**:
   - Once a report is generated and saved, a CSV file is created with aggregated store report data.
   - Users can download the CSV file through the `/api/get_report` endpoint.
