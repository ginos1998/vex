-- trigger executed before updating the stock of a product
CREATE OR REPLACE TRIGGER tr_update_product_when_stock_changes
              BEFORE UPDATE OF stock ON product
    FOR EACH ROW
EXECUTE FUNCTION f_update_product_when_stock_changes();