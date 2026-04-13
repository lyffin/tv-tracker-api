# tv-tracker-api
API of tv tracker app

/media/search?query={query}&type={type}

query ($id: Int) {
Media (id: $id, type: ANIME) {
id
title {
romaji
english
}
averageScore
coverImage {
large
}
startDate {
year
}
format
status
endDate {
year
}
description
duration
episodes
streamingEpisodes {
thumbnail
title
}
relations {
edges {
relationType
node {
id
coverImage {
large
}
averageScore
startDate {
year
}
format
title {
english
romaji
}
}
}
}
}
}
