# MusicFestival
This is coding test app for EA

# Assumptions and applied solutions
- App doesn't handle HTTP 429 yet. Refresh again if app keeps loading.
- Following data issues were found and handled:
  - Record labels can be received as "", will be handled as same and will be shown as Unsigned Artists
  - Data can be received as "". Will be shown as no data received
  - Festivals can be received with no name field. Is handled as a non existent festival. Bands under the object will be added but that festival will not be shown.

# Improvements that can be done
- FestivalService can be added as part of Service layer to separate local and network data sources
- FetchFestivalUseCase can be created and data sorting can be moved to use case instead of repository, so that repository layer only fetches/stores data
- Currently code only tests data conversion logic, more Unit, integration and UI tests can be added.