CREATE OR REPLACE FUNCTION f_get_product_by_branch_using_filter(
    p_code VARCHAR,
    p_category_id INTEGER,
    p_page_size INTEGER,
    p_branch_id INTEGER,
    p_name VARCHAR,
    p_page_number INTEGER,
    p_sort VARCHAR,
    p_supplier_id INTEGER,
    p_brand_id INTEGER,
    p_order VARCHAR
)


    RETURNS TABLE(product_id INTEGER,
                  branch_id INTEGER,
                  category_id INTEGER,
                  name VARCHAR(100),
                  code VARCHAR(20),
                  price DECIMAL(10, 2),
                  buy_price DECIMAL(10, 2),
                  stock INTEGER,
                  description VARCHAR(100),
                  iva_rate DECIMAL(4, 2),
                  created_at TIMESTAMP,
                  updated_at TIMESTAMP,
                  enabled CHAR,
                  supplier_id INTEGER,
                  brand_id INTEGER)
    LANGUAGE plpgsql
AS
$$
DECLARE
    q_select TEXT;
    q_join TEXT;
    q_where TEXT;
    q_order TEXT;
    q_limit TEXT;
BEGIN
    q_select := 'SELECT p.product_id as p_product_id,
                            p.branch_id as p_branch_id,
                            p.category_id as p_category_id,
                            p.name as p_name,
                            p.code as p_code,
                            p.price as p_price,
                            p.buy_price as p_buy_price,
                            p.stock as p_stock,
                            p.description as p_description,
                            p.iva_rate as p_iva_rate,
                            p.created_at as p_created_at,
                            p.updated_at as p_updated_at,
                            p.enabled as p_enabled,
                            p.supplier_id as p_supplier_id,
                            p.brand_id as p_brand_id
                            FROM product p ';

    q_join := ' INNER JOIN branch b on p.branch_id = b.branch_id ';
    q_where := ' WHERE p.branch_id = ' || p_branch_id || ' AND p.enabled = ''Y'' ';

    IF p_category_id IS NOT NULL AND p_category_id > 0 THEN
        q_join := q_join || ' INNER JOIN category c on p.category_id = c.category_id ';
        q_where := q_where || ' AND p.category_id = ' || p_category_id;
    END IF;

    IF p_name IS NOT NULL THEN
        q_where := q_where || ' AND p.name ilike ''%' || p_name || '%'' ';
    END IF;

    IF p_code IS NOT NULL THEN
        q_where := q_where || ' AND p.code ilike ''%' || p_code || '%'' ';
    END IF;

    IF p_supplier_id IS NOT NULL AND p_supplier_id > 0 THEN
        q_join := q_join || ' INNER JOIN supplier s on p.supplier_id = s.supplier_id ';
        q_where := q_where || ' AND p.supplier_id = ' || p_supplier_id;
    END IF;

    IF p_brand_id IS NOT NULL AND p_brand_id > 0 THEN
        q_join := q_join || ' INNER JOIN brand br on p.brand_id = br.brand_id ';
        q_where := q_where || ' AND p.brand_id = ' || p_brand_id;
    END IF;

    CASE p_sort
        WHEN 'id' THEN
            q_order := ' ORDER BY p.product_id ' || p_order;
        WHEN 'name' THEN
            q_order := ' ORDER BY p.name ' || p_order;
        ELSE
            q_order := ' ORDER BY p.product_id ' || p_order;
        END CASE;

    q_limit := ' LIMIT ' || p_page_size || ' OFFSET ' || (p_page_number - 1) * p_page_size;

    RETURN QUERY EXECUTE q_select || q_join || q_where || q_order || q_limit;
END;
$$;