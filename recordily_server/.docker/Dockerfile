FROM php:8.1-fpm-bullseye

RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer

RUN apt-get update
RUN apt-get install -y libzip-dev
RUN docker-php-ext-install zip
RUN docker-php-ext-install mysqli pdo pdo_mysql
RUN docker-php-ext-configure pcntl --enable-pcntl \
  && docker-php-ext-install \
    pcntl
RUN pecl install -o -f redis \
&&  rm -rf /tmp/pear \
&&  docker-php-ext-enable redis
