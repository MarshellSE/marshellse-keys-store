INSERT INTO
    usuario_gamer (alias, email, saldo_wallet, cuenta_activa)
VALUES
    ('Jordano', 'U23309199@utp.edu.pe', 150.00, true);

-- Insertando videojuegos en el catalogo
INSERT INTO
    videojuego (titulo, desarrollador, precio_base, stock)
VALUES
    ('Grand Theft Auto V', 'Rockstar Games', 29.99, 2);

INSERT INTO
    videojuego (titulo, desarrollador, precio_base, stock)
VALUES
    ('Minecraft', 'Mojang', 26.95, 1);

INSERT INTO
    videojuego (titulo, desarrollador, precio_base, stock)
VALUES
    (
        'The Witcher 3: Wild Hunt',
        'CD Projekt',
        39.99,
        0
    );

-- Insertando keys asociadas a los juegos
INSERT INTO
    licencia_key (
        videojuego_id,
        codigo,
        region_activacion,
        estado_disponible
    )
VALUES
    (1, 'GTA5-GLB-1234-ABCD', 'Global', true);

INSERT INTO
    licencia_key (
        videojuego_id,
        codigo,
        region_activacion,
        estado_disponible
    )
VALUES
    (1, 'GTA5-GLB-5678-EFGH', 'Global', true);

INSERT INTO
    licencia_key (
        videojuego_id,
        codigo,
        region_activacion,
        estado_disponible
    )
VALUES
    (2, 'MC-JAVA-9999-ZZZZ', 'Global', true);