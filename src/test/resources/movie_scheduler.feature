Feature: user can schedule event for a movie

  Scenario Outline: user schedules a movie
    Given there is a movie in repository
      | movieId | premiere      | in3d       | movieDuration    |
      | abc123  | <is_premiere> | <is_in_3d> | <movie_duration> |
    And there is a room in repository with scheduled event
      | roomId | isAvailable      | scheduledStart    | scheduledEnd    |
      | def123 | <room_available> | <scheduled_start> | <scheduled_end> |
    When service is called with movie event
      | movieId | roomId | eventStart    |
      | abc123  | def123 | <event_start> |
    Then service responds with data
      | responseCode    | 3d         |
      | <response_code> | <is_in_3d> |

    Examples:
      | is_premiere | is_in_3d | movie_duration | room_available | event_start              | response_code               | scheduled_start          | scheduled_end            |
      | false       | false    | PT90M          | true           | 20-06-2022T12:34:56+0200 | OK                          |                          |                          |
      | false       | true     | PT90M          | true           | 20-06-2022T12:34:56+0200 | OK                          |                          |                          |
      | false       | false    | PT90M          | false          | 20-06-2022T12:34:56+0200 | ROOM_NOT_AVAILABLE          |                          |                          |
      | false       | false    | PT90M          | true           | 20-06-2022T05:34:56+0200 | MOVIE_OUTSIDE_WORKING_HOURS |                          |                          |
      | false       | false    | PT90M          | true           | 20-06-2022T23:34:56+0200 | MOVIE_OUTSIDE_WORKING_HOURS |                          |                          |
      | false       | false    | PT90M          | true           | 20-06-2022T16:34:56+0200 | MOVIE_IS_OVERLAPPING        | 20-06-2022T15:34:56+0200 | 20-06-2022T17:34:56+0200 |
      | false       | false    | PT90M          | true           | 20-06-2022T16:34:56+0200 | MOVIE_IS_OVERLAPPING        | 20-06-2022T18:34:56+0200 | 20-06-2022T20:34:56+0200 |