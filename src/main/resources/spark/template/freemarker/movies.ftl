<html>
    <head>
        <title>Harbour.Space IMDb</title>
        <link rel="stylesheet" href="/style.css">
    </head>
    <body>
        <header>Harbour.Space IMDb</header><br>

        <#list movies as id, movie>
            <section>
                <head>Title: ${movie.title}</head><br>
                <img src=${movie.poster} alt="Poster">
                <p>Id: ${id}</p>
                <p>Year: ${movie.year}</p>
                <p>Director: ${movie.director}</p>
                <a class="details" href=movies/${id}>Details</a><br>
            </section>
        </#list>

        <footer>Java Programming - Harbour.Space University</footer>
    </body>
</html>