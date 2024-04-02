-- function triggered when stock changes
CREATE OR REPLACE FUNCTION f_update_product_when_stock_changes()
RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NEW.stock = 0 THEN
        NEW.enabled := 'N';
END IF;
RETURN NEW;
END;
$$;