# vaadin-blood-sample-editor

## Running the project

This project requires Gradle 4.0.

`gradle vaadinRun`

Wait for the application to start. Open http://localhost:8080/ to view the application.

## Running unit tests

`gradle test`

## Documentation

### Backend

This application does not use database. The backend data is hardcoded.

`Plate` contains information about a plate. The plate contains 0..* samples, which are in represented in the `Sample` class. Settings for the plate are defined in `PlateSettings` object. It contains information about the number of rows and columns (currently hardcoded to 96 well) and also the max volume of a sample.


### Front-end

I did not use model-view-presenter pattern because I feel it adds to much overhead to an otherwise simple project.

`BloodSampleEditor` is the main UI component. It is the visual representation of a `Plate` object. The view contains a grid (96 well). Each grid cell contains one Well object.

`Well` is the UI element of a well. The well might be empty or it might contain a `Sample` object. A well can be clicked, which opens a `SampleWindow` modal window.

`SampleWindow` is used to modify the information of the sample in a well, for example move it to another well or modify the volume. If you click an empty well, you can then use `SampleWindow` to add a new sample to the plate.

### Front-end helper methods

`WellHelper` is used in determining the color of a blood sample. It uses the sample volume and the defined max volume to scale the color.

`GridAdapter` does coordinates mapping between the sample coordinates to the Vaadin `Grid` component cell coordinates. For example, it maps "B1" to "21". Note that rows and columns are mapped separately. It also does the reverse, so "21" will be mapped to "B1".

`LocationValidator` validates that the text field values must be contained in the predefined list. For example, we give the list of accepted rows (A, B, .., H) and the text field value must match one of the items in the list.

`StringHelper` is used for formatting numbers to strings and also splitting strings nicely to two rows.