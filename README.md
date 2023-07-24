## How to run:
Build and install on any android device or emulator. You can change the value within FetchStockService to change the end point being called (to mock error or empty state)

## Architecture:
I mainly focused on separation of concerns and data flow allowing for low coupling and high cohesion while keeping the codebase as simple as possible. 

I have used a simple MVVM with repository pattern.

I have a service layer consisting of a retrofit service, a singleton API client instantiating retrofit and working like a util class and the stock DTO.
This helps keep all the retrofit details within the same package and the rest of the app can be unaware of the underlying networking needs.

I have a repository which I have doing two things, it makes the network call and then it translates it to a data model that the rest of the app can understand.
By doing the transformation during this phase allows for the rest of the app to not need to know what the mobile - backend contracts looks like making it easier to migrate and change these models as needed.

I have a ui package which contains all of the UI information - the composables and the theme.

I have also created a StockViewModel which contains a LiveData to keep track of the StockDataModel returned from the repository. This ViewModel is also coupled with our UI and so it handles whether the UI should be in LOADING, ERROR or SUCCESS (showing the response) state.

Lastly, I have a MainActivity that initializes the ViewModel and sets the UI. I went with a simple single activity no fragment approach here mostly because it would be quick and simple. The overhead of moving this to a fragment and changing this to be a single activity multiple fragment architecture in future iterations should be minimal.

## Third party libraries and copied code:
I have copied the LiveDataTestUils and some of the test setup for coroutine.

Just used Retrofit for network calls. Used mockito and truth library for testing.

## Note:
I spent roughly 2 and half hours total spread across 2 sessions.

I am more familiar with rx rather than coroutine, flows and live data but I really wanted to take this opportunity to test these out.
