-- dtype is added to implement inheritance through Hibernate using @DiscriminationColumn
ALTER TABLE
  user
ADD
  dtype VARCHAR(31);