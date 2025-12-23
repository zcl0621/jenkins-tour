"""create users table

Revision ID: 0001
Revises:
Create Date: 2025-12-23 18:42:00.000000
"""

from alembic import op
import sqlalchemy as sa

revision = "0001"
down_revision = None
branch_labels = None
depends_on = None


def upgrade() -> None:
    op.create_table(
        "users",
        sa.Column("id", sa.Integer(), primary_key=True),
        sa.Column("email", sa.String(length=255), nullable=False, unique=True),
    )


def downgrade() -> None:
    op.drop_table("users")
