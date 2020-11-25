<html>
    <head>
        <title>${movie.title}</title>
        <link rel="stylesheet" href="/style.css">
    </head>
    <body>
        <header>"${movie.title}"</header>

        <section>
            <img src=${movie.poster} alt="Poster">
            <p>Year: ${movie.year}</p>
            <p>Director: ${movie.director}</p>
            <p>Plot: ${movie.plot}</p>
            <p>Runtime: ${movie.runtime} minutes</p>
            <p>Award: ${movie.awards}</p>

        </section>

        <footer>Java Programming - Harbour.Space University</footer>
    </body>
</html>