create or replace function f_get_all_sub_categories(p_category_id integer, p_sub_category_name character varying)
    returns TABLE(sc_sub_category_id integer, sc_category_id integer, sc_sub_category_name character varying, sc_available character, c_category_id integer, c_category_name character varying, c_available character)
    language plpgsql
as
$$
DECLARE
    q_select_clause varchar := '';
    q_where_clause varchar := '';
BEGIN
    q_select_clause := ' SELECT sc.sub_category_id   as sc_sub_category_id,
                               sc.category_id       as sc_category_id,
                               sc.sub_category_name as sc_sub_category_name,
                               sc.available         as sc_available,
                               c.category_id        as c_category_id,
                               c.category_name      as c_category_name,
                               c.available          as c_available
                       FROM sub_category sc
                            INNER JOIN category c ON sc.category_id = c.category_id
                       WHERE sc.available = ''Y'' AND c.available = ''Y'' ';

    IF p_category_id IS NOT NULL AND p_category_id > 0 THEN
        q_where_clause := q_where_clause || ' AND c.category_id = ' || p_category_id;
    END IF;

    IF p_sub_category_name IS NOT NULL AND p_sub_category_name <> '' THEN
        q_where_clause := q_where_clause || ' AND sc.sub_category_name ILIKE ''' || p_sub_category_name || '''';
    END IF;

    RETURN QUERY EXECUTE q_select_clause || q_where_clause;
END;
$$;

alter function f_get_all_sub_categories(integer, varchar) owner to root;

